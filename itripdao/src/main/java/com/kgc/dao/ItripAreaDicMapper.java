package com.kgc.dao;
import com.kgc.beans.model.ItripAreaDic;
import com.kgc.beans.vo.BizCityIdAndNameVo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * ItripAreaDicMapper
 * 李文俊
 * 2020.7.20
 */
public interface ItripAreaDicMapper {

	public ItripAreaDic getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripAreaDic>	getListByMap(Map<String, Object> param)throws Exception;

	public List<BizCityIdAndNameVo>	getIdAndNameListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripAreaDic itripAreaDic)throws Exception;

	public Integer modify(ItripAreaDic itripAreaDic)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
