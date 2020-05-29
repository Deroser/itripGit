package com.kgc.service.impl;

import com.kgc.beans.model.ItripHotelOrder;
import com.kgc.dao.ItripHotelOrderMapper;
import com.kgc.service.OrderService;
import com.kgc.utils.EmptyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;
    @Override
    public ItripHotelOrder loadItripHotelOrder(String orderNo) throws Exception {
        Map<String ,Object> param = new HashMap<>();
        param.put("orderNo",orderNo);
        List<ItripHotelOrder> listByMap = itripHotelOrderMapper.getListByMap(param);
        if (EmptyUtils.isNotEmpty(listByMap)){
            return listByMap.get(0);
        }
        return null;
    }
}
