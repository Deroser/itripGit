package com.kgc.service;
import com.kgc.beans.model.ItripHotelRoom;
import java.util.List;
import java.util.Map;

import com.kgc.beans.model.ItripHotelRoom;
import com.kgc.beans.vo.ItripHotelRoomAndTimeVo;
import com.kgc.beans.vo.ItripHotelRoomVo;
import com.kgc.utils.Page;

/**
 * ItripHotelRoomService
 * 李文俊
 * 2020.7.20
 */
public interface ItripHotelRoomService {

    public ItripHotelRoom getById(Long id)throws Exception;

    public List<ItripHotelRoom>	getListByMap(Map<String, Object> param)throws Exception;

    public List<List<ItripHotelRoomVo>>getNoTimeList(ItripHotelRoomAndTimeVo vo)throws Exception;

    public Integer getCountByMap(Map<String, Object> param)throws Exception;

    public Integer save(ItripHotelRoom itripHotelRoom)throws Exception;

    public Integer modify(ItripHotelRoom itripHotelRoom)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public Page<List<ItripHotelRoom>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
