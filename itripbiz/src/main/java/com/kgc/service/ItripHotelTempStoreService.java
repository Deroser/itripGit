package com.kgc.service;
import java.util.List;
import java.util.Map;

import com.kgc.beans.model.ItripHotelTempStore;
import com.kgc.beans.vo.store.StoreVO;
import com.kgc.utils.Page;


public interface ItripHotelTempStoreService {

    public ItripHotelTempStore getById(Long id)throws Exception;

    public List<ItripHotelTempStore>	getListByMap(Map<String, Object> param)throws Exception;

    public Integer getCountByMap(Map<String, Object> param)throws Exception;

    public Integer save(ItripHotelTempStore itripHotelTempStore)throws Exception;

    public Integer modify(ItripHotelTempStore itripHotelTempStore)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public Page<List<ItripHotelTempStore>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;

    public List<StoreVO> queryRoomStore(Map<String, Object> param)throws Exception;

    public boolean validateRoomStore(Map<String, Object> param)throws Exception;

    public Integer updateRoomStore(Map<String, Object> param)throws Exception;
}
