package com.kgc.dao;
import com.kgc.beans.model.ItripHotelTradingArea;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * ItripHotelTradingAreaMapper
 * 李文俊
 * 2020.7.20
 */
public interface ItripHotelTradingAreaMapper {

	public ItripHotelTradingArea getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripHotelTradingArea>	getListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripHotelTradingArea itripHotelTradingArea)throws Exception;

	public Integer modify(ItripHotelTradingArea itripHotelTradingArea)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
