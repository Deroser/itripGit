package com.kgc.dao;
import com.kgc.beans.model.ItripHotelOrder;
import com.kgc.beans.vo.*;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripHotelOrderMapper {

	public ItripHotelOrder getById(@Param(value = "id") Long id)throws Exception;

	public ItripHotelOrderVo getQueryOrderById(@Param(value = "id") Long id)throws Exception;

	public ItripPersonalOrderRoomVO getPersonalOrderRoomInfoByOrderid(@Param(value = "id") Long id)throws Exception;

	public List<ItripPersonalOrderRoomVO> getPersonalOrderRoomInfoByHotelid(@Param(value = "id") Long id)throws Exception;

	public List<ItripHotelOrder>	getListByMap(Map<String, Object> param)throws Exception;

	public List<ItripPersonalOrderVo>getPersonaLorderListByMap(ItripSearchOrderVO vo)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer getPersonaLorderCountByMap(ItripSearchOrderVO vo)throws Exception;

	public Integer save(ItripHotelOrder itripHotelOrder)throws Exception;

	public Integer modify(ItripHotelOrder itripHotelOrder)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

	public Integer flushCancelOrderStatus()throws Exception;

	public Integer flushSuccessOrderStatus()throws Exception;

}
