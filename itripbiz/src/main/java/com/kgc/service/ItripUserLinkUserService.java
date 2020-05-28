package com.kgc.service;

import com.kgc.beans.model.ItripUserLinkUser;
import com.kgc.utils.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ItripUserLinkUserService {

    public ItripUserLinkUser getById(Long id)throws Exception;

    public List<ItripUserLinkUser>	getListByMap(Map<String, Object> param)throws Exception;

    public Integer getCountByMap(Map<String, Object> param)throws Exception;

    public boolean whetherToPay(Long id)throws Exception;

    public boolean save(ItripUserLinkUser itripUserLinkUser)throws Exception;

    public boolean modify(ItripUserLinkUser itripUserLinkUser)throws Exception;

    public boolean removeById(Long id)throws Exception;

    public Page<List<ItripUserLinkUser>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
