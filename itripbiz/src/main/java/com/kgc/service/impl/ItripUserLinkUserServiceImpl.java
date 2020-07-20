package com.kgc.service.impl;

import com.kgc.beans.model.ItripUserLinkUser;
import com.kgc.dao.ItripUserLinkUserMapper;
import com.kgc.service.ItripUserLinkUserService;
import com.kgc.utils.Constants;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * ItripUserLinkUserServiceImpl
 * 李文俊
 * 2020.7.20
 */
@Service("itripUserLinkUserService")
public class ItripUserLinkUserServiceImpl implements ItripUserLinkUserService {

    @Resource
    private ItripUserLinkUserMapper itripUserLinkUserMapper;

    public ItripUserLinkUser getById(Long id) throws Exception {
        return itripUserLinkUserMapper.getById(id);
    }

    public List<ItripUserLinkUser> getListByMap(Map<String, Object> param) throws Exception {
        return itripUserLinkUserMapper.getListByMap(param);
    }

    public Integer getCountByMap(Map<String, Object> param) throws Exception {
        return itripUserLinkUserMapper.getCountByMap(param);
    }

    @Override
    public boolean whetherToPay(Long id) throws Exception {
        if (itripUserLinkUserMapper.getNopaidCountById(id)>0){
            return false;
        }
        return true;
    }

    public boolean save(ItripUserLinkUser itripUserLinkUser) throws Exception {
        itripUserLinkUser.setCreationDate(new Date());
        if (itripUserLinkUserMapper.save(itripUserLinkUser) > 0) {
            return true;
        }
        return false;
    }

    public boolean modify(ItripUserLinkUser itripUserLinkUser) throws Exception {
        itripUserLinkUser.setModifyDate(new Date());
        if (itripUserLinkUserMapper.modify(itripUserLinkUser) > 0) {
            return true;
        }
        return false;
    }

    public boolean removeById(Long id) throws Exception {
        if (itripUserLinkUserMapper.removeById(id) > 0) {
            return true;
        }
        return false;
    }

    public Page<List<ItripUserLinkUser>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = itripUserLinkUserMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripUserLinkUser> itripUserLinkUserList = itripUserLinkUserMapper.getListByMap(param);
        page.setRows(itripUserLinkUserList);
        return page;
    }

}
