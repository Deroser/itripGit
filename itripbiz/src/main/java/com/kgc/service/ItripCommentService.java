package com.kgc.service;
import java.util.List;
import java.util.Map;

import com.kgc.beans.model.ItripComment;
import com.kgc.beans.model.ItripCommentData;
import com.kgc.beans.vo.ItripCommentCountVo;
import com.kgc.beans.vo.ItripCommentScoreAvgVo;
import com.kgc.beans.vo.ItripSearchCommentVO;
import com.kgc.utils.Page;


public interface ItripCommentService {

    public ItripComment getById(Long id)throws Exception;

    public ItripCommentScoreAvgVo getScoreAvgByHotelId(Long hotelId)throws Exception;

    public ItripCommentCountVo getCommentCountByHotelId(Long hotelId)throws Exception;

    public List<ItripCommentData>getCommentListByHotelId(ItripSearchCommentVO itripSearchCommentVO)throws Exception;


    public List<ItripComment>	getListByMap(Map<String, Object> param)throws Exception;

    public Integer getCountByMap(Map<String, Object> param)throws Exception;

    public boolean save(ItripComment itripComment)throws Exception;

    public Integer modify(ItripComment itripComment)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public Page<List<ItripComment>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
