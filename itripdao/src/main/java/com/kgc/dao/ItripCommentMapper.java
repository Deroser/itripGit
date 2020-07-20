package com.kgc.dao;
import com.kgc.beans.model.ItripComment;
import com.kgc.beans.model.ItripCommentData;
import com.kgc.beans.vo.ItripCommentCountVo;
import com.kgc.beans.vo.ItripCommentScoreAvgVo;
import com.kgc.beans.vo.ItripSearchCommentVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * ItripCommentMapper
 * 李文俊
 * 2020.7.20
 */
public interface ItripCommentMapper {

	public ItripComment getById(@Param(value = "id") Long id)throws Exception;

	public ItripCommentScoreAvgVo getScoreAvgByHotelId(@Param(value = "hotelId") Long hotelId)throws Exception;

	public ItripCommentCountVo getCommentCountByHotelId(@Param(value = "hotelId") Long hotelId)throws Exception;

	public List<ItripComment>	getListByMap(Map<String, Object> param)throws Exception;

	public List<ItripCommentData>getCommentListByHotelId(ItripSearchCommentVO itripSearchCommentVO)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripComment itripComment)throws Exception;

	public Integer modify(ItripComment itripComment)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
