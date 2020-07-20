package com.kgc.dao;
import com.kgc.beans.model.ItripTradeEnds;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * ItripTradeEndsMapper
 * 李文俊
 * 2020.7.20
 */
public interface ItripTradeEndsMapper {

	public ItripTradeEnds getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripTradeEnds>	getListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripTradeEnds itripTradeEnds)throws Exception;

	public Integer modify(ItripTradeEnds itripTradeEnds)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

	public Integer updateItripTradeEnds(Map<String, Object> param)throws Exception;

}
