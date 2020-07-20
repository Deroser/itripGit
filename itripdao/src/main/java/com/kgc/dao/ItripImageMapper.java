package com.kgc.dao;
import com.kgc.beans.model.ItripImage;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * ItripImageMapper
 * 李文俊
 * 2020.7.20
 */
public interface ItripImageMapper {

	public ItripImage getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripImage> getImgById(@Param(value = "id") Long id,
									   @Param(value = "type") String type )throws Exception;

	public List<ItripImage>	getListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripImage itripImage)throws Exception;

	public Integer modify(ItripImage itripImage)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
