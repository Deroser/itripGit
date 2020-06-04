package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kgc.beans.model.*;
import com.kgc.beans.vo.*;
import com.kgc.beans.vo.store.StoreVO;
import com.kgc.service.*;
import com.kgc.utils.*;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/hotelorder")
public class HotelOrderController {
    private Logger logger = Logger.getLogger(HotelOrderController.class);
    @Autowired
    private RedisAPI redisAPI;
    @Resource
    private TokenService biztokenService;
    @Resource
    private ItripHotelOrderService itripHotelOrderService;
    @Resource
    private ItripHotelService itripHotelService;
    @Resource
    private ItripHotelRoomService itripHotelRoomService;
    @Resource
    private ItripHotelTempStoreService itripHotelTempStoreService;
    @Resource
    private SystemConfig systemConfig;
    @Resource
    private ItripTradeEndsService itripTradeEndsService;

    @RequestMapping(value = "/getpersonalorderlist", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto getpersonalorderlist(@RequestBody ItripSearchOrderVO vo,
                                    HttpServletRequest request) {

        //验证token
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        }

        if (EmptyUtils.isEmpty(vo)) {
            return DtoUtil.returnFail("数据异常", "10020");
        }
        if (EmptyUtils.isEmpty(vo.getOrderStatus())) {
            return DtoUtil.returnFail("数据异常", "10502");
        }
        if (EmptyUtils.isEmpty(vo.getOrderType())) {
            return DtoUtil.returnFail("请传递参数：orderType", "10501");
        }
        ItripDataVo data = new ItripDataVo();
        if (vo.getPageNo() == null) {
            vo.setPageNo(1);
        }
        vo.setBeginPos((vo.getPageNo() - 1) * vo.getPageSize());
        try {
            String[] tokenDeteils = token.split("-");
            vo.setUserId(Long.valueOf(tokenDeteils[2]));
            List<ItripPersonalOrderVo> list = itripHotelOrderService.getPersonaLorderListByMap(vo);
            data.setRows(list);
            data.setTotal(itripHotelOrderService.getPersonaLorderCountByMap(vo));
            data.setBeginPos(vo.getBeginPos());
            data.setCurPage(vo.getPageNo());
            data.setPageSize(vo.getPageSize());
            data.setPageCount(data.getTotal() % data.getPageSize() == 0 ? data.getTotal() / data.getPageSize() : data.getTotal() / data.getPageSize() + 1);
            return DtoUtil.returnSuccess("获取个人订单列表成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取个人订单列表错误", "10503");
        }
    }

    @RequestMapping(value = "/getpersonalorderroominfo/{orderId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto getpersonalorderroominfo(@PathVariable String orderId,
                                        HttpServletRequest request) {
        //验证token
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        }
        if (EmptyUtils.isEmpty(orderId)) {
            return DtoUtil.returnFail("请传递参数：orderId", "10529");
        }
        ItripPersonalOrderRoomVO data = null;
        List<ItripPersonalOrderRoomVO> dataList = null;
        try {
            data = itripHotelOrderService.getPersonalOrderRoomInfoByOrderid(Long.valueOf(orderId));
            if (EmptyUtils.isEmpty(data)) {
                dataList = itripHotelOrderService.getPersonalOrderRoomInfoByHotelid(Long.valueOf(orderId));
                if (EmptyUtils.isEmpty(dataList)) {
                    return DtoUtil.returnFail("没有相关订单房型信息 ", "10530");
                }
                return DtoUtil.returnSuccess("获取酒店房型信息成功", dataList);
            }
            return DtoUtil.returnSuccess("获取个人订单房型信息成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取个人订单房型信息错误", "10531");
        }
    }

    @RequestMapping(value = "/getpersonalorderinfo/{orderId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto getpersonalorderinfo(@PathVariable String orderId,
                                    HttpServletRequest request) {
        //验证token
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        }
        if (EmptyUtils.isEmpty(orderId)) {
            return DtoUtil.returnFail("请传递参数：orderId", "10529");
        }
        ItripUser currentUser = JSONObject.parseObject(redisAPI.get(token), ItripUser.class);
        try {
            ItripHotelOrder itripHotelOrder = itripHotelOrderService.getById(Long.valueOf(orderId));
            if (EmptyUtils.isNotEmpty(itripHotelOrder)){
                ItripPersonalHotelOrderVO itripPersonalHotelOrderVO = new ItripPersonalHotelOrderVO();
                BeanUtils.copyProperties(itripHotelOrder,itripPersonalHotelOrderVO);
                //查询预定房间信息
                ItripHotelRoom byId = itripHotelRoomService.getById(itripHotelOrder.getRoomId());
                if (EmptyUtils.isNotEmpty(byId)){
                    itripPersonalHotelOrderVO.setRoomPayType(itripHotelOrder.getPayType());
                }
                Integer orderStatus = itripHotelOrder.getOrderStatus();
                itripPersonalHotelOrderVO.setOrderStatus(orderStatus);
                if (orderStatus == 1){
                    itripPersonalHotelOrderVO.setOrderProcess(JSONArray.parse(systemConfig.getOrderProcessCancel()));
                    itripPersonalHotelOrderVO.setProcessNode("3");
                }else if (orderStatus == 0){
                    itripPersonalHotelOrderVO.setOrderProcess(JSONArray.parse(systemConfig.getOrderProcessOK()));
                    itripPersonalHotelOrderVO.setProcessNode("2");//待支付
                }else if (orderStatus == 2){
                    itripPersonalHotelOrderVO.setOrderProcess(JSONArray.parse(systemConfig.getOrderProcessOK()));
                    itripPersonalHotelOrderVO.setProcessNode("3");//支付成功，未出行
                }else if (orderStatus == 3){
                    itripPersonalHotelOrderVO.setOrderProcess(JSONArray.parse(systemConfig.getOrderProcessOK()));
                    itripPersonalHotelOrderVO.setProcessNode("5");//订单点评
                }else if (orderStatus == 4){
                    itripPersonalHotelOrderVO.setOrderProcess(JSONArray.parse(systemConfig.getOrderProcessOK()));
                    itripPersonalHotelOrderVO.setProcessNode("6");//订单完成
                }else {
                    itripPersonalHotelOrderVO.setOrderProcess(null);
                    itripPersonalHotelOrderVO.setProcessNode(null);
                }
                return DtoUtil.returnSuccess("获取相关订单信息成功",itripPersonalHotelOrderVO);
            }else {
                return DtoUtil.returnFail("没有相关订单信息", "10526");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取个人订单信息错误", "10527");
        }
    }

    @RequestMapping(value = "/queryOrderById/{orderId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto queryOrderById(@PathVariable String orderId,
                              HttpServletRequest request) {
        //验证token
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        }
        if (EmptyUtils.isEmpty(orderId)) {
            return DtoUtil.returnFail("订单id不能为空", "10533");
        }
        ItripHotelOrderVo data = null;
        try {
            data = itripHotelOrderService.getQueryOrderById(Long.valueOf(orderId));
            if (EmptyUtils.isEmpty(data)) {
                return DtoUtil.returnFail("没有查询到相应订单", "10533");
            }
            return DtoUtil.returnSuccess("获取订单信息成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", "10534");
        }
    }

    @RequestMapping(value = "/getpreorderinfo", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto<RoomStoreVO> getPreorderInfo(@RequestBody ValidateRoomStoreVO vo,
                                            HttpServletRequest request) {
        //验证token
        String token = request.getHeader("token");
        ItripUser currentUser = JSONObject.parseObject(redisAPI.get(token), ItripUser.class);
        ItripHotel hotel = null;
        ItripHotelRoom room = null;
        RoomStoreVO roomStoreVO = null;
        try {
            if (EmptyUtils.isEmpty(currentUser)) {
                return DtoUtil.returnFail("token失效，请重新登录", "10000");
            }
            if (EmptyUtils.isEmpty(vo.getHotelId())) {
                return DtoUtil.returnFail("hotelId不能为空", "10510");
            } else if (EmptyUtils.isEmpty(vo.getRoomId())) {
                return DtoUtil.returnFail("roomId不能为空", "10511");
            } else {
                roomStoreVO = new RoomStoreVO();
                hotel = itripHotelService.getById(vo.getHotelId());
                room = itripHotelRoomService.getById(vo.getRoomId());
                roomStoreVO.setCheckInDate(vo.getCheckInDate());
                roomStoreVO.setCheckOutDate(vo.getCheckOutDate());
                roomStoreVO.setHotelName(hotel.getHotelName());
                roomStoreVO.setPrice(room.getRoomPrice());
                roomStoreVO.setHotelId(vo.getHotelId());
                Map<String, Object> param = new HashMap<>();
                param.put("startTime", vo.getCheckInDate());
                param.put("endTime", vo.getCheckOutDate());
                param.put("roomId", vo.getRoomId());
                param.put("hotelId", vo.getHotelId());
                List<StoreVO> storeVOS = itripHotelTempStoreService.queryRoomStore(param);
                roomStoreVO.setCount(1);
                if (EmptyUtils.isNotEmpty(storeVOS)) {
                    roomStoreVO.setStore(storeVOS.get(0).getStore());
                } else {
                    return DtoUtil.returnFail("暂时无房", "10512");
                }
                return DtoUtil.returnSuccess("获取成功", roomStoreVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", "10513");
        }

    }

    @RequestMapping(value = "/validateroomstore", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto<RoomStoreVO> validateRoomStore(@RequestBody ValidateRoomStoreVO vo,
                                              HttpServletRequest request) {
        //验证token
        String token = request.getHeader("token");
        ItripUser currentUser = JSONObject.parseObject(redisAPI.get(token), ItripUser.class);
        try {
            if (EmptyUtils.isEmpty(currentUser)) {
                return DtoUtil.returnFail("token失效，请重新登录", "10000");
            }
            if (EmptyUtils.isEmpty(vo.getHotelId())) {
                return DtoUtil.returnFail("hotelId不能为空", "10515");
            } else if (EmptyUtils.isEmpty(vo.getRoomId())) {
                return DtoUtil.returnFail("roomId不能为空", "10516");
            } else {
                Map<String, Object> param = new HashMap<>();
                param.put("startTime", vo.getCheckInDate());
                param.put("endTime", vo.getCheckOutDate());
                param.put("roomId", vo.getRoomId());
                param.put("hotelId", vo.getHotelId());
                param.put("count", vo.getCount());
                boolean flag = itripHotelTempStoreService.validateRoomStore(param);
                Map<String, Object> map = new HashMap<>();
                map.put("flag", flag);
                return DtoUtil.returnSuccess("操作成功", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", "10517");
        }
    }

    @RequestMapping(value = "/addhotelorder", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto<RoomStoreVO> addhotelorder(@RequestBody ItripAddHotelOrderVO vo,
                                          HttpServletRequest request) {
        //验证token
        String token = request.getHeader("token");
        ItripUser currentUser = JSONObject.parseObject(redisAPI.get(token), ItripUser.class);
        if (EmptyUtils.isEmpty(currentUser)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        }
        if (EmptyUtils.isEmpty(vo)) {
            return DtoUtil.returnFail("不能提交空", "10506");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("startTime", vo.getCheckInDate());
        param.put("endTime", vo.getCheckOutDate());
        param.put("roomId", vo.getRoomId());
        param.put("hotelId", vo.getHotelId());
        param.put("count", vo.getCount());
        List<ItripUserLinkUser> linkUserList = vo.getLinkUser();
        try {
            boolean flag = itripHotelTempStoreService.validateRoomStore(param);
            //判断库存
            if (flag){
                //计算订单预定天数
                Integer days = DateUtil.getBetweenDates(vo.getCheckInDate(), vo.getCheckOutDate()).size()-1;
                if (days <= 0){
                    return DtoUtil.returnFail("退房时间必须大于入住时间","10505");
                }
                ItripHotelOrder itripHotelOrder = new ItripHotelOrder();
                BeanUtils.copyProperties(vo,itripHotelOrder);
                itripHotelOrder.setUserId(currentUser.getId());
                itripHotelOrder.setCreatedBy(currentUser.getId());
                StringBuffer linkUserName = new StringBuffer();
                for (int i = 0; i < linkUserList.size(); i++) {
                     if (i != linkUserList.size()-1){
                         linkUserName.append(linkUserList.get(i).getLinkUserName()+",");
                     }else {
                         linkUserName.append(linkUserList.get(i).getLinkUserName());
                     }
                }
                itripHotelOrder.setLinkUserName(linkUserName.toString());
                itripHotelOrder.setBookingDays(days);
                //平台
                if (token.startsWith("token:PC")){
                    itripHotelOrder.setBookType(0);
                }else if(token.startsWith("token:MOBILE")){
                    itripHotelOrder.setBookType(1);
                }else {
                    itripHotelOrder.setBookType(2);
                }
                //订单状态：未支付
                itripHotelOrder.setOrderStatus(0);
                //产品类型
                itripHotelOrder.setOrderType(1);
                //订单号：机器码+日期+（MD5）（商品ids+当前毫秒数+1000000随机数）
                StringBuffer md5Str = new StringBuffer();
                md5Str.append(vo.getHotelId());
                md5Str.append(vo.getRoomId());
                md5Str.append(System.currentTimeMillis());
                md5Str.append(Math.random()*1000000);
                String md5 = MD5.getMd5(md5Str.toString(), 6);
                //生成订单号
                StringBuffer orderNo = new StringBuffer();
                orderNo.append(systemConfig.getMachineCode());
                orderNo.append(DateUtil.format(new Date(),"yyyyMMddHHmmss"));
                orderNo.append(md5);
                itripHotelOrder.setOrderNo(orderNo.toString());
                //计算订单金额
                BigDecimal orderPayAmount = itripHotelOrderService.getOrderPayAmount(vo.getCount() * days, vo.getRoomId());
                itripHotelOrder.setPayAmount(orderPayAmount);
                Map<String, Object> map = itripHotelOrderService.addItripOrderAsLinkUser(itripHotelOrder, linkUserList);
                return DtoUtil.returnSuccess("生成订单成功",map);
            }else {
                return DtoUtil.returnFail("库存不足", "10507");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //系统异常
            return DtoUtil.returnFail("系统异常", "10517");
        }
    }

    @RequestMapping(value = "/scanTradeEnd", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto scanTradeEnd(){
        Map<String ,Object> map = new HashMap();
        map.put("flag","1");
        map.put("oldFlag","0");
        try {
            itripTradeEndsService.itripModifyItripTradeEnds(map);
            List<ItripTradeEnds> listByMap = itripTradeEndsService.getListByMap(map);
            if (EmptyUtils.isNotEmpty(listByMap)){
                for (ItripTradeEnds itripTradeEnds : listByMap) {
                    Map<String ,Object> param = new HashMap();
                    param.put("orderNo",itripTradeEnds.getOrderNo());
                    List<ItripHotelOrder> listByMap1 = itripHotelOrderService.getListByMap(param);
                    for (ItripHotelOrder itripHotelOrder : listByMap1) {
                        Map<String, Object> roomStoreMap = new HashMap<>();
                        roomStoreMap.put("startTime", itripHotelOrder.getCheckInDate());
                        roomStoreMap.put("endTime", itripHotelOrder.getCheckOutDate());
                        roomStoreMap.put("roomId", itripHotelOrder.getRoomId());
                        roomStoreMap.put("count", itripHotelOrder.getCount());
                        itripHotelTempStoreService.updateRoomStore(roomStoreMap);
                    }
                }
                map.put("flag","2");
                map.put("oldFlag","1");
                itripTradeEndsService.itripModifyItripTradeEnds(map);
                return DtoUtil.returnSuccess();
            }else {
                return DtoUtil.returnFail("没有查询到相应记录","10535");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常","10536");
        }

    }

    //10分钟刷新一次
    @Scheduled(cron = "0 0/10 * * * ?")
    public void flushCancelStatus(){
        logger.info("执行刷新任务=======================10");
        try {
            boolean flag = itripHotelOrderService.flushOrderStatus(1);
            logger.info(flag ? "刷新成功" : "刷新失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //100分钟刷新一次
    @Scheduled(cron = "0 0/100 * * * ?")
    public void flushOrderStatus(){
        logger.info("执行刷新任务=======================23");
        try {
            boolean flag = itripHotelOrderService.flushOrderStatus(0);
            logger.info(flag ? "刷新成功" : "刷新失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/querysuccessorderinfo/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto querysuccessorderinfo(@PathVariable Long id,HttpServletRequest request){
        //验证token
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        }
        if (EmptyUtils.isEmpty(id)) {
            return DtoUtil.returnFail("id不能为空", "10519");
        }
        try {
            ItripHotelOrder itripHotelOrder = itripHotelOrderService.getById(id);
            ItripHotelRoom itripHotelRoom = itripHotelRoomService.getById(id);
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("orderNo",itripHotelOrder.getOrderNo());
            resultMap.put("payType",itripHotelOrder.getPayType());
            resultMap.put("payAmount",itripHotelOrder.getPayAmount());
            resultMap.put("id",itripHotelOrder.getId());
            resultMap.put("roomTitle",itripHotelRoom.getRoomTitle());
            resultMap.put("hotelName",itripHotelOrder.getHotelName());
            return DtoUtil.returnSuccess("获取成功",resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取数据失败","10520");
        }
    }

    @RequestMapping(value = "/updateorderstatusandpaytype", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto<RoomStoreVO> updateorderstatusandpaytype(@RequestBody ItripModifyHotelOrderVO vo,
                                          HttpServletRequest request){
        //验证token
        String token = request.getHeader("token");
        ItripUser currentUser = JSONObject.parseObject(redisAPI.get(token), ItripUser.class);
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        }
        if (EmptyUtils.isEmpty(vo)) {
            return DtoUtil.returnFail("不能提交空，请填写订单信息", "10523");
        }
        ItripHotelOrder itripHotelOrder = new ItripHotelOrder();
        BeanUtils.copyProperties(vo,itripHotelOrder);
        itripHotelOrder.setOrderStatus(2);
        itripHotelOrder.setModifiedBy(currentUser.getId());
        try {
            Integer flag = itripHotelOrderService.modify(itripHotelOrder);
            if (flag > 0){
                return DtoUtil.returnSuccess("修改订单成功");
            }
            return DtoUtil.returnFail("修改订单失败","10522");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("修改订单失败","10522");
        }
    }
}
