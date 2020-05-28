package com.kgc.controller;

import com.kgc.beans.model.ItripImage;
import com.kgc.beans.model.ItripLabelDic;
import com.kgc.beans.vo.ItripHotelRoomAndTimeVo;
import com.kgc.beans.vo.ItripHotelRoomVo;
import com.kgc.beans.vo.ItripLabelDicVo;
import com.kgc.service.ItripHotelRoomService;
import com.kgc.service.ItripImageService;
import com.kgc.service.ItripLabelDicService;
import com.kgc.utils.Dto;
import com.kgc.utils.DtoUtil;
import com.kgc.utils.EmptyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api/hotelroom")
public class HotelRoomController {
    @Resource
    private ItripHotelRoomService itripHotelRoomService;
    @Resource
    private ItripLabelDicService itripLabelDicService;

    @Resource
    private ItripImageService itripImageService;

    @RequestMapping(value = "/getimg/{targetId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getimg(@PathVariable String targetId){
        if(EmptyUtils.isEmpty(targetId)){
            return DtoUtil.returnFail("酒店id不能为空", "10213");
        }
        List<ItripImage> data = null;
        try {
            data = itripImageService.getHotelroomImgById(Long.valueOf(targetId));
            return DtoUtil.returnSuccess("获取酒店房型图片成功",data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店房型图片失败", "10212");
        }
    }

    @RequestMapping(value = "/queryhotelroombed",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhotelroombed(){
        List<ItripLabelDicVo> hotelFeatureList = null;
        try {
            hotelFeatureList = itripLabelDicService.getHotelRoomBedList();
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店房间床型失败", "10305");
        }
        return DtoUtil.returnDataSuccess(hotelFeatureList);
    }

    @RequestMapping(value = "/queryhotelroombyhotel",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto queryhotelroombyhotel(@RequestBody ItripHotelRoomAndTimeVo itripHotelRoomAndTimeVo){
        if (EmptyUtils.isEmpty(itripHotelRoomAndTimeVo.getHotelId())){
            return DtoUtil.returnFail("酒店id不能为空", "10303");
        }
        if (EmptyUtils.isEmpty(itripHotelRoomAndTimeVo.getEndDate())||EmptyUtils.isEmpty(itripHotelRoomAndTimeVo.getStartDate())){
            return DtoUtil.returnFail("酒店入住及退房时间不能为空", "10303");
        }
        if (itripHotelRoomAndTimeVo.getEndDate().getTime()< itripHotelRoomAndTimeVo.getStartDate().getTime()){
            return DtoUtil.returnFail("入住时间不能大于退房时间", "10303");
        }
        List<List<ItripHotelRoomVo>> noTimeList = null;
        try {
            noTimeList = itripHotelRoomService.getNoTimeList(itripHotelRoomAndTimeVo);
            return DtoUtil.returnSuccess("查询酒店房间列表成功",noTimeList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", "10304");
        }
    }
}
