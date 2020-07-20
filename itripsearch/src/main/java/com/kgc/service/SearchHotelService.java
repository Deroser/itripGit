package com.kgc.service;

import com.kgc.beans.vo.SearchHotCityVO;
import com.kgc.beans.vo.SearchHotelVO;
import com.kgc.beans.vo.hotel.ItripHotelVo;
import com.kgc.dao.Param;
import com.kgc.dao.SolrParam;
import com.kgc.utils.Page;
import io.swagger.models.auth.In;

import java.util.List;
/**
 * SearchHotelService
 * 李文俊
 * 2020.7.20
 */
public interface SearchHotelService {
    /**
     * 根据热门城市查询酒店
     */
    public List<ItripHotelVo> searchItripHotelListByHotCity(SolrParam param)throws Exception;

    /**
     * 多条件查询酒店信息
     * @param
     * @return
     * @throws Exception
     */
    public Page<ItripHotelVo> searchItripHotelPage(SearchHotelVO vo, Integer pageNo, Integer pageSize)throws Exception;
}
