package com.kgc.dao;
import com.kgc.beans.model.ItripProductStore;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * ItripProductStoreMapper
 * 李文俊
 * 2020.7.20
 */
public interface ItripProductStoreMapper {

	public ItripProductStore getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripProductStore>	getListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripProductStore itripProductStore)throws Exception;

	public Integer modify(ItripProductStore itripProductStore)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
