package com.kgc.dao;
import com.kgc.beans.model.ItripHotelFeature;
import com.kgc.beans.vo.BizVideoDescVo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * ItripHotelFeatureMapper
 * 李文俊
 * 2020.7.20
 */
public interface ItripHotelFeatureMapper {

	public ItripHotelFeature getById(@Param(value = "id") Long id)throws Exception;

	public BizVideoDescVo getVideoDescByHotelId(@Param(value = "hotelId") String  hotelId)throws Exception;

	public List<ItripHotelFeature>	getListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripHotelFeature itripHotelFeature)throws Exception;

	public Integer modify(ItripHotelFeature itripHotelFeature)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
