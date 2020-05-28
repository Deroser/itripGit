package com.kgc.controller;

import com.kgc.beans.vo.SearchHotCityVO;
import com.kgc.beans.vo.SearchHotelVO;
import com.kgc.beans.vo.hotel.ItripHotelVo;
import com.kgc.dao.Param;
import com.kgc.dao.SolrParam;
import com.kgc.service.SearchHotelService;
import com.kgc.utils.Dto;
import com.kgc.utils.DtoUtil;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api/hotellist")
public class HotelListController {
    @Resource
    private SearchHotelService searchHotelService;

    /**
     * 根据城市id查询酒店信息
     * @param searchHotCityVO
     * @return
     */
    @RequestMapping(value = "/searchItripHotelListByHotCity",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto<Page<ItripHotelVo>> searchItripHotelListByHotCity(@RequestBody SearchHotCityVO searchHotCityVO){
        if (EmptyUtils.isEmpty(searchHotCityVO)||EmptyUtils.isEmpty(searchHotCityVO.getCityId())){
            return DtoUtil.returnFail("城市id不能为空","20000");
        }
        try {
            SolrParam solrParam = new SolrParam();
            solrParam.add("cityId",searchHotCityVO.getCityId(),":");
            solrParam.add("count",searchHotCityVO.getCount(),":");
            List<ItripHotelVo> itripHotelVos = searchHotelService.searchItripHotelListByHotCity(solrParam);
            return DtoUtil.returnDataSuccess(itripHotelVos);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常","20001");
        }

    }
    /**
     * 多条件查询酒店信息
     */
    @RequestMapping(value = "/searchItripHotelPage",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto searchItripHotelPage(@RequestBody SearchHotelVO searchHotelVO){
        Page page = null;
        if (EmptyUtils.isEmpty(searchHotelVO.getDestination())||EmptyUtils.isEmpty(searchHotelVO)){
            return DtoUtil.returnFail("目的地不能为空","20000");
        }
        try {
            page = searchHotelService.searchItripHotelPage(searchHotelVO,searchHotelVO.getPageNo(),searchHotelVO.getPageSize());
            return DtoUtil.returnDataSuccess(page);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常","20001");
        }

    }

}
