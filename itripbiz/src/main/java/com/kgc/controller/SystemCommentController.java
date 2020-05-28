package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kgc.beans.model.*;
import com.kgc.beans.vo.*;
import com.kgc.service.*;
import com.kgc.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/api/comment")
public class SystemCommentController {

    @Resource
    private ItripLabelDicService itripLabelDicService;

    @Resource
    private ItripHotelService itripHotelService;

    @Resource
    private ItripCommentService itripCommentService;

    @Resource
    private ItripImageService itripImageService;

    @Autowired
    private RedisAPI redisAPI;

    @Resource
    private TokenService biztokenService;

    @RequestMapping(value = "/gettraveltype", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto gettraveltype() {
        List<ItripLabelDicVo> hotelFeatureList = null;
        try {
            hotelFeatureList = itripLabelDicService.getTravelTypeList();
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取旅游类型列表错误", "10019");
        }
        return DtoUtil.returnDataSuccess(hotelFeatureList);
    }

    @RequestMapping(value = "/getimg/{targetId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto getimg(@PathVariable String targetId) {
        if (EmptyUtils.isEmpty(targetId)) {
            return DtoUtil.returnFail("酒店id不能为空", "10213");
        }
        List<ItripImage> data = null;
        try {
            data = itripImageService.getTalkImgById(Long.valueOf(targetId));
            return DtoUtil.returnSuccess("获取酒店房型图片成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店房型图片失败", "10212");
        }
    }

    @RequestMapping(value = "/gethotelscore/{hotelId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto gethotelscore(@PathVariable String hotelId) {
        if (EmptyUtils.isEmpty(hotelId)) {
            return DtoUtil.returnFail("酒店id不能为空", "10002");
        }
        ItripCommentScoreAvgVo data = null;
        try {
            data = itripCommentService.getScoreAvgByHotelId(Long.valueOf(hotelId));
            return DtoUtil.returnSuccess("获取评分成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取评分失败", "10001");
        }
    }

    @RequestMapping(value = "/gethoteldesc/{hotelId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto gethoteldesc(@PathVariable String hotelId) {
        if (EmptyUtils.isEmpty(hotelId)) {
            return DtoUtil.returnFail("酒店id不能为空", "10020");
        }
        ItripCommentHotelDescVo vo = new ItripCommentHotelDescVo();
        try {
            ItripHotel data = itripHotelService.getById(Long.valueOf(hotelId));
            vo.setHotelLevel(data.getHotelLevel());
            vo.setHotelId(data.getId());
            vo.setHotelName(data.getHotelName());
            return DtoUtil.returnDataSuccess(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店相关信息错误", "10021");
        }
    }

    @RequestMapping(value = "/getcount/{hotelId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto getcount(@PathVariable String hotelId) {
        if (EmptyUtils.isEmpty(hotelId)) {
            return DtoUtil.returnFail("酒店id为空", "10018");
        }
        ItripCommentCountVo data = null;
        try {
            data = itripCommentService.getCommentCountByHotelId(Long.valueOf(hotelId));
            return DtoUtil.returnSuccess("获取酒店各类评论数成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店各类评论数失败", "10017");
        }
    }

    @RequestMapping(value = "/getcommentlist", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto getcommentlist(@RequestBody ItripSearchCommentVO vo) {
        if (EmptyUtils.isEmpty(vo)) {
            return DtoUtil.returnFail("数据异常", "10020");
        }
        ItripDataVo data = new ItripDataVo();
        vo.setBeginPos((vo.getPageNo() - 1) * vo.getPageSize());
        try {
            List<ItripCommentData> lists = itripCommentService.getCommentListByHotelId(vo);
            Map<String, Object> param = new HashMap<>();
            param.put("hotelId", vo.getHotelId());
            param.put("isOk", vo.getIsOk());
            param.put("isHavingImg", vo.getIsHavingImg());
            data.setRows(lists);
            data.setTotal(itripCommentService.getCountByMap(param));
            data.setBeginPos(vo.getBeginPos());
            data.setCurPage(vo.getPageNo());
            data.setPageSize(vo.getPageSize());
            data.setPageCount(data.getTotal() % data.getPageSize() == 0 ? data.getTotal() / data.getPageSize() : data.getTotal() / data.getPageSize() + 1);
            return DtoUtil.returnSuccess("获取评论列表成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取评论列表错误", "10017");
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto add(@RequestBody ItripAddCommentVO vo,HttpServletRequest request) {
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        }
        if (EmptyUtils.isEmpty(vo.getContent())){
            return DtoUtil.returnFail("不能提交空，请填写评论信息", "10004");
        }
        if (EmptyUtils.isEmpty(vo.getOrderId())){
            return DtoUtil.returnFail("新增评论，订单ID不能为空", "10005");
        }
        try {
            ItripComment itripComment = new ItripComment();
            String[] tokenDeteils = token.split("-");
            itripComment.setUserId(Long.valueOf(tokenDeteils[2]));
            itripComment.setCreatedBy(Long.valueOf(tokenDeteils[2]));
            BeanUtils.copyProperties(vo,itripComment);
            int avg = vo.getFacilitiesScore()+
                    vo.getHygieneScore()+
                    vo.getServiceScore()+
                    vo.getPositionScore();
            itripComment.setScore(avg%4<2?avg/4:avg/4+1);
            boolean flag = itripCommentService.save(itripComment);
            if (!flag){
                return DtoUtil.returnFail("新增评论失败", "10003");
            }
            Map<String ,Object> param = new HashMap<>();
            param.put("creationDate",itripComment.getCreationDate());
            param.put("content",vo.getContent());
            List<ItripComment> list = itripCommentService.getListByMap(param);
            ItripImage[] itripImages = vo.getItripImages();
            int num = 0;
            for (ItripImage itripImage : itripImages) {
                itripImage.setTargetId(list.get(0).getId());
                itripImage.setType("2");
                itripImage.setPosition(++num);
                itripImage.setCreatedBy(Long.valueOf(tokenDeteils[2]));
                if (!itripImageService.save(itripImage)){
                    return DtoUtil.returnFail("新增评论图片失败", "10003");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.ERROR);
        }
        return DtoUtil.returnSuccess("新增评论成功");
    }
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto<Object> upload(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
        Dto<Object> dto = new Dto<Object>();
        List<String> dataList = new ArrayList<String>();
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            int fileCount = 0;
            try {
                fileCount = multiRequest.getFileMap().size();
            } catch (Exception e) {
                // TODO: handle exception
                fileCount = 0;
                return DtoUtil.returnFail("文件大小超限", "10009");
            }
            if (fileCount > 0 && fileCount <= 4) {
                String tokenString = multiRequest.getHeader("token");
                String s = redisAPI.get(tokenString);
                JSONObject json = JSONObject.parseObject(s);
                ItripUser itripUser = JSON.toJavaObject(json, ItripUser.class);
                if (null != itripUser) {
                    //取得request中的所有文件名
                    Iterator<String> iter = multiRequest.getFileNames();
                    while (iter.hasNext()) {
                        try {
                            //取得上传文件
                            MultipartFile file = multiRequest.getFile(iter.next());
                            if (file != null) {
                                //取得当前上传文件的文件名称
                                String myFileName = file.getOriginalFilename();
                                //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                                if (myFileName.trim() != "" && (myFileName.toLowerCase().contains(".jpg")
                                        || myFileName.toLowerCase().contains(".jpeg")
                                        || myFileName.toLowerCase().contains(".png")
                                )) {
                                    //重命名上传后的文件名
                                    // 命名规则：用户id+当前时间+随机数
                                    String suffixString = myFileName.substring(file.getOriginalFilename().indexOf("."));
                                    String fileName = itripUser.getId() + "-" + System.currentTimeMillis() + "-" + ((int) (Math.random() * 10000000)) + suffixString;
                                    //定义上传路径
                                    String path = request.getSession().getServletContext().getRealPath(File.separator + fileName);
                                    File localFile = new File(path);
                                    file.transferTo(localFile);
//                                    dataList.add("http://img.itrip.test.com/comment/"+fileName);
                                    dataList.add(path);
                                }
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    dto = DtoUtil.returnSuccess("文件上传成功", dataList);
                } else {
                    dto = DtoUtil.returnFail("文件上传失败", "100006");
                }
            } else {
                dto = DtoUtil.returnFail("上传的文件数不正确，必须是大于1小于等于4", "100007");
            }
        } else {
            dto = DtoUtil.returnFail("请求的内容不是上传文件的类型", "100008");
        }
        return dto;
    }

    @RequestMapping(value = "/delpic", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto<Object> delpic(@RequestParam(value = "imgName") String imgName,HttpServletRequest request){
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        }
        if (EmptyUtils.isEmpty(imgName)){
            return DtoUtil.returnFail("无效的文件名", "10010");
        }
        String path = request.getSession().getServletContext().getRealPath(File.separator + imgName);
        File oldfile = new File(path);
        if (!oldfile.exists()){
            return DtoUtil.returnFail("文件不存在，删除失败", "10010");
        }
        oldfile.delete();
        return DtoUtil.returnSuccess("删除传递图片名称成功");
    }
}
