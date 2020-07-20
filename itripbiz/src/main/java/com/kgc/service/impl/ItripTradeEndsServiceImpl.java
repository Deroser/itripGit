package com.kgc.service.impl;
import com.kgc.beans.model.ItripTradeEnds;
import com.kgc.service.ItripTradeEndsService;
import com.kgc.dao.ItripTradeEndsMapper;
import com.kgc.utils.Constants;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * ItripTradeEndsServiceImpl
 * 李文俊
 * 2020.7.20
 */
@Service("itripTradeEndsService")
public class ItripTradeEndsServiceImpl implements ItripTradeEndsService {

    @Resource
    private ItripTradeEndsMapper itripTradeEndsMapper;

    public ItripTradeEnds getById(Long id)throws Exception{
        return itripTradeEndsMapper.getById(id);
    }

    public List<ItripTradeEnds> getListByMap(Map<String,Object> param)throws Exception{
        return itripTradeEndsMapper.getListByMap(param);
    }

    public Integer getCountByMap(Map<String,Object> param)throws Exception{
        return itripTradeEndsMapper.getCountByMap(param);
    }

    public Integer save(ItripTradeEnds itripTradeEnds)throws Exception{
            return itripTradeEndsMapper.save(itripTradeEnds);
    }

    public Integer modify(ItripTradeEnds itripTradeEnds)throws Exception{
        return itripTradeEndsMapper.modify(itripTradeEnds);
    }

    public Integer removeById(Long id)throws Exception{
        return itripTradeEndsMapper.removeById(id);
    }

    public Page<List<ItripTradeEnds>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripTradeEndsMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripTradeEnds> itripTradeEndsList = itripTradeEndsMapper.getListByMap(param);
        page.setRows(itripTradeEndsList);
        return page;
    }

    @Override
    public void itripModifyItripTradeEnds(Map<String, Object> param) throws Exception {
        itripTradeEndsMapper.updateItripTradeEnds(param);
    }

}
