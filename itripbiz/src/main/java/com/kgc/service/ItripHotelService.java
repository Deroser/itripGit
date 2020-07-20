package com.kgc.service;

import com.kgc.beans.model.ItripBizHotelDeteil;
import com.kgc.beans.model.ItripHotel;
import com.kgc.utils.Page;

import java.util.List;
import java.util.Map;
/**
 * ItripHotelService
 * 李文俊
 * 2020.7.20
 */
public interface ItripHotelService {
    public String  gethotelPolicyById(Long id)throws Exception;

    public ItripHotel getById(Long id)throws Exception;

    public String  getfacilitiesById(Long id)throws Exception;

    public List<ItripBizHotelDeteil> getHotelDetailsById(String id)throws Exception;

    public List<ItripHotel>	getListByMap(Map<String,Object> param)throws Exception;

    public Integer getCountByMap(Map<String,Object> param)throws Exception;

    public Integer save(ItripHotel itripHotel)throws Exception;

    public Integer modify(ItripHotel itripHotel)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public Page<List<ItripHotel>> queryPageByMap(Map<String,Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
