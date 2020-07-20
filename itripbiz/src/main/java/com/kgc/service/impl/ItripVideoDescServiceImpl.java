package com.kgc.service.impl;

import com.kgc.beans.vo.BizVideoDescVo;
import com.kgc.dao.ItripHotelFeatureMapper;
import com.kgc.service.ItripVideoDescService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
 * ItripVideoDescServiceImpl
 * 李文俊
 * 2020.7.20
 */
@Service("itripVideoDescService")
public class ItripVideoDescServiceImpl implements ItripVideoDescService {
    @Resource
    private ItripHotelFeatureMapper itripHotelFeatureMapper;
    @Override
    public BizVideoDescVo getVideoDescByHotelId(String  hotelId) throws Exception {
        return itripHotelFeatureMapper.getVideoDescByHotelId(hotelId);
    }
}
