package com.kgc.controller;

import com.kgc.beans.model.ItripAreaDic;
import com.kgc.beans.model.ItripBizHotelDeteil;
import com.kgc.beans.model.ItripImage;
import com.kgc.beans.model.ItripLabelDic;
import com.kgc.beans.vo.BizCityIdAndNameVo;
import com.kgc.beans.vo.BizVideoDescVo;
import com.kgc.beans.vo.ItripLabelDicVo;
import com.kgc.service.*;
import com.kgc.service.impl.ItripVideoDescServiceImpl;
import com.kgc.utils.Dto;
import com.kgc.utils.DtoUtil;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.ErrorCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/hotel")
public class HotelController {
    @Resource
    private ItripLabelDicService itripLabelDicService;
    @Resource
    private ItripAreaDicService itripAreaDicService;
    @Resource
    private ItripVideoDescService itripVideoDescService;
    @Resource
    private ItripHotelService itripHotelService;
    @Resource
    private ItripImageService itripImageService;

    @RequestMapping(value = "/queryhotelfeature",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhotelfeature(){
        List<ItripLabelDicVo> hotelFeatureList = null;
        try {
            hotelFeatureList = itripLabelDicService.getHotelFeatureList();
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.ERROR);
        }
        return DtoUtil.returnDataSuccess(hotelFeatureList);
    }

    @RequestMapping(value = "/queryhotcity/{type}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhotcity(@PathVariable String type){
        List<BizCityIdAndNameVo> hotCityList = null;
        if (EmptyUtils.isEmpty(type)){
            return DtoUtil.returnFail("hotelid不能为空", "10201");
        }
        try {
            hotCityList = itripAreaDicService.getHotCityListByIsChina(type);
            return DtoUtil.returnDataSuccess(hotCityList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常，获取失败", "10202");
        }
    }

    @RequestMapping(value = "/querytradearea/{cityId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto querytradearea(@PathVariable String cityId){
        List<BizCityIdAndNameVo> areaList = null;
        if(EmptyUtils.isEmpty(cityId)){
            return DtoUtil.returnFail("cityId不能为空", "10203");
        }
        try {
            areaList = itripAreaDicService.getAreaByIsChina(cityId);
            return DtoUtil.returnDataSuccess(areaList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常，获取失败", "10204");
        }
    }
    @RequestMapping(value = "/getvideodesc/{hotelId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getvideodesc(@PathVariable String hotelId){
        if(EmptyUtils.isEmpty(hotelId)){
            return DtoUtil.returnFail("hotelId不能为空", "10215");
        }
        BizVideoDescVo bizVideoDescVo = null;
        try {
            bizVideoDescVo = itripVideoDescService.getVideoDescByHotelId(hotelId);
            return DtoUtil.returnSuccess("获取酒店视频文字描述成功",bizVideoDescVo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店视频文字描述失败", "10214");
        }
    }

    @RequestMapping(value = "/queryhotelpolicy/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhotelpolicy(@PathVariable String id){
        if(EmptyUtils.isEmpty(id)){
            return DtoUtil.returnFail("酒店id不能为空", "10208");
        }
        String  data = null;
        try {
            data = itripHotelService.gethotelPolicyById(Long.valueOf(id));
            return DtoUtil.returnDataSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败", "10209");
        }
    }

    @RequestMapping(value = "/queryhotelfacilities/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhotelfacilities(@PathVariable String id){
        if(EmptyUtils.isEmpty(id)){
            return DtoUtil.returnFail("酒店id不能为空", "10206");
        }
        String  data = null;
        try {
            data = itripHotelService.getfacilitiesById(Long.valueOf(id));
            return DtoUtil.returnDataSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败", "10207");
        }
    }

    @RequestMapping(value = "/queryhoteldetails/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhoteldetails(@PathVariable String id){
        if(EmptyUtils.isEmpty(id)){
            return DtoUtil.returnFail("酒店id不能为空", "10210");
        }
        List<ItripBizHotelDeteil>  data = null;
        try {
            data = itripHotelService.getHotelDetailsById(id);
            return DtoUtil.returnDataSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败", "10211");
        }
    }

    @RequestMapping(value = "/getimg/{targetId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getimg(@PathVariable String targetId){
        if(EmptyUtils.isEmpty(targetId)){
            return DtoUtil.returnFail("酒店房型id不能为空", "10302");
        }
        List<ItripImage> data = null;
        try {
            data = itripImageService.getHotelImgById(Long.valueOf(targetId));
            return DtoUtil.returnSuccess("获取酒店图片成功",data);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店图片失败", "10301");
        }
    }
}
