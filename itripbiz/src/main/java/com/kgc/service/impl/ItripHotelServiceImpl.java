package com.kgc.service.impl;

import com.kgc.beans.model.ItripBizHotelDeteil;
import com.kgc.beans.model.ItripHotel;
import com.kgc.dao.ItripHotelMapper;
import com.kgc.service.ItripHotelService;
import com.kgc.utils.Constants;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripHotelService")
public class ItripHotelServiceImpl implements ItripHotelService {
    @Resource
    private ItripHotelMapper itripHotelMapper;

    @Override
    public String  gethotelPolicyById(Long id)throws Exception {
        ItripHotel hotel = itripHotelMapper.getById(id);
        String hotelPolicy = hotel.getHotelPolicy();
        return hotelPolicy;
    }

    @Override
    public ItripHotel getById(Long id) throws Exception {
        return itripHotelMapper.getById(id);
    }

    @Override
    public String getfacilitiesById(Long id) throws Exception {
        ItripHotel hotel = itripHotelMapper.getById(id);
        String facilities = hotel.getFacilities();
        return facilities;
    }

    @Override
    public List<ItripBizHotelDeteil> getHotelDetailsById(String id) throws Exception {
        List<ItripBizHotelDeteil> list = itripHotelMapper.getHotelDetailsById(id).getList();
        ItripBizHotelDeteil itripBizHotelDeteil = new ItripBizHotelDeteil();
        itripBizHotelDeteil.setName("酒店介绍");
        itripBizHotelDeteil.setDescription(itripHotelMapper.getHotelDetailsById(id).getDescription());
        list.add(0,itripBizHotelDeteil);
        return list;
    }
    @Override
    public List<ItripHotel> getListByMap(Map<String,Object> param)throws Exception{
        return itripHotelMapper.getListByMap(param);
    }
    @Override
    public Integer getCountByMap(Map<String,Object> param)throws Exception{
        return itripHotelMapper.getCountByMap(param);
    }
    @Override
    public Integer save(ItripHotel itripHotel)throws Exception{
        itripHotel.setCreationDate(new Date());
        return itripHotelMapper.save(itripHotel);
    }
    @Override
    public Integer modify(ItripHotel itripHotel)throws Exception{
        itripHotel.setModifyDate(new Date());
        return itripHotelMapper.modify(itripHotel);
    }
    @Override
    public Integer removeById(Long id)throws Exception{
        return itripHotelMapper.removeById(id);
    }
    @Override
    public Page<List<ItripHotel>> queryPageByMap(Map<String,Object> param, Integer pageNo, Integer pageSize)throws Exception{
        Integer total = itripHotelMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotel> itripHotelList = itripHotelMapper.getListByMap(param);
        page.setRows(itripHotelList);
        return page;
    }
}
