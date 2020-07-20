package com.kgc.service.impl;
import com.kgc.beans.model.ItripComment;
import com.kgc.beans.model.ItripCommentData;
import com.kgc.beans.vo.ItripCommentCountVo;
import com.kgc.beans.vo.ItripCommentScoreAvgVo;
import com.kgc.beans.vo.ItripSearchCommentVO;
import com.kgc.service.ItripCommentService;
import com.kgc.dao.ItripCommentMapper;
import com.kgc.utils.Constants;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * ItripCommentServiceImpl
 * 李文俊
 * 2020.7.20
 */
@Service("itripCommentService")
public class ItripCommentServiceImpl implements ItripCommentService {

    @Resource
    private ItripCommentMapper itripCommentMapper;

    public ItripComment getById(Long id)throws Exception{
        return itripCommentMapper.getById(id);
    }

    @Override
    public ItripCommentScoreAvgVo getScoreAvgByHotelId(Long hotelId) throws Exception {
        return itripCommentMapper.getScoreAvgByHotelId(hotelId);
    }

    @Override
    public ItripCommentCountVo getCommentCountByHotelId(Long hotelId) throws Exception {
        return itripCommentMapper.getCommentCountByHotelId(hotelId);
    }

    @Override
    public List<ItripCommentData> getCommentListByHotelId(ItripSearchCommentVO itripSearchCommentVO) throws Exception {
        return itripCommentMapper.getCommentListByHotelId(itripSearchCommentVO);
    }

    public List<ItripComment> getListByMap(Map<String,Object> param)throws Exception{
        return itripCommentMapper.getListByMap(param);
    }

    public Integer getCountByMap(Map<String,Object> param)throws Exception{
        return itripCommentMapper.getCountByMap(param);
    }

    public boolean save(ItripComment itripComment)throws Exception{
            itripComment.setCreationDate(new Date());
            if (itripCommentMapper.save(itripComment)>0){
                return true;
            }
            return false;
    }

    public Integer modify(ItripComment itripComment)throws Exception{
        itripComment.setModifyDate(new Date());
        return itripCommentMapper.modify(itripComment);
    }

    public Integer removeById(Long id)throws Exception{
        return itripCommentMapper.removeById(id);
    }

    public Page<List<ItripComment>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripCommentMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripComment> itripCommentList = itripCommentMapper.getListByMap(param);
        page.setRows(itripCommentList);
        return page;
    }

}
