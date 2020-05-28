package com.kgc.dao;
import com.kgc.beans.model.ItripHotelTempStore;
import com.kgc.beans.vo.store.StoreVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripHotelTempStoreMapper {

	public ItripHotelTempStore getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripHotelTempStore>	getListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripHotelTempStore itripHotelTempStore)throws Exception;

	public Integer modify(ItripHotelTempStore itripHotelTempStore)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

	public void flushStore(Map<String, Object> param)throws Exception;

	public List<StoreVO> queryRoomStore(Map<String, Object> param)throws Exception;

	public Integer updateRoomStore(Map<String, Object> param)throws Exception;

}
