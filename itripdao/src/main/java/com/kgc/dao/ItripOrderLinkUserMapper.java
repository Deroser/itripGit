package com.kgc.dao;
import com.kgc.beans.model.ItripOrderLinkUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * ItripOrderLinkUserMapper
 * 李文俊
 * 2020.7.20
 */
public interface ItripOrderLinkUserMapper {

	public ItripOrderLinkUser getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripOrderLinkUser>	getListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripOrderLinkUser itripOrderLinkUser)throws Exception;

	public Integer modify(ItripOrderLinkUser itripOrderLinkUser)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
