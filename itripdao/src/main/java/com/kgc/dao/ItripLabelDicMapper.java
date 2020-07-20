package com.kgc.dao;
import com.kgc.beans.model.ItripLabelDic;
import com.kgc.beans.vo.ItripLabelDicVo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * ItripLabelDicMapper
 * 李文俊
 * 2020.7.20
 */
public interface ItripLabelDicMapper {

	public ItripLabelDic getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripLabelDic>	getListByMap(Map<String, Object> param)throws Exception;

	public List<ItripLabelDicVo> getLabelDicVoListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripLabelDic itripLabelDic)throws Exception;

	public Integer modify(ItripLabelDic itripLabelDic)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
