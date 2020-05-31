package com.kgc.service.impl;

import com.kgc.beans.model.ItripHotelOrder;
import com.kgc.beans.model.ItripOrderLinkUser;
import com.kgc.beans.model.ItripTradeEnds;
import com.kgc.beans.model.ItripUserLinkUser;
import com.kgc.beans.vo.*;
import com.kgc.dao.ItripHotelRoomMapper;
import com.kgc.dao.ItripOrderLinkUserMapper;
import com.kgc.dao.ItripTradeEndsMapper;
import com.kgc.service.ItripHotelOrderService;
import com.kgc.dao.ItripHotelOrderMapper;
import com.kgc.utils.BigDecimalUtil;
import com.kgc.utils.Constants;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_DOWN;

@Service("itripHotelOrderService")
public class ItripHotelOrderServiceImpl implements ItripHotelOrderService {

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;
    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;
    @Resource
    private ItripOrderLinkUserMapper itripOrderLinkUserMapper;
    @Resource
    private ItripTradeEndsMapper itripTradeEndsMapper;

    public ItripHotelOrder getById(Long id) throws Exception {
        return itripHotelOrderMapper.getById(id);
    }

    @Override
    public ItripHotelOrderVo getQueryOrderById(Long id) throws Exception {
        return itripHotelOrderMapper.getQueryOrderById(id);
    }

    @Override
    public ItripPersonalOrderRoomVO getPersonalOrderRoomInfoByOrderid(Long id) throws Exception {
        return itripHotelOrderMapper.getPersonalOrderRoomInfoByOrderid(id);
    }

    @Override
    public List<ItripPersonalOrderRoomVO> getPersonalOrderRoomInfoByHotelid(Long id) throws Exception {
        return itripHotelOrderMapper.getPersonalOrderRoomInfoByHotelid(id);
    }

    public List<ItripHotelOrder> getListByMap(Map<String, Object> param) throws Exception {
        return itripHotelOrderMapper.getListByMap(param);
    }

    @Override
    public List<ItripPersonalOrderVo> getPersonaLorderListByMap(ItripSearchOrderVO vo) throws Exception {
        return itripHotelOrderMapper.getPersonaLorderListByMap(vo);
    }

    public Integer getCountByMap(Map<String, Object> param) throws Exception {
        return itripHotelOrderMapper.getCountByMap(param);
    }

    @Override
    public Integer getPersonaLorderCountByMap(ItripSearchOrderVO vo) throws Exception {
        return itripHotelOrderMapper.getPersonaLorderCountByMap(vo);
    }

    public Integer save(ItripHotelOrder itripHotelOrder) throws Exception {
        itripHotelOrder.setCreationDate(new Date());
        return itripHotelOrderMapper.save(itripHotelOrder);
    }

    public Integer modify(ItripHotelOrder itripHotelOrder) throws Exception {
        itripHotelOrder.setModifyDate(new Date());
        return itripHotelOrderMapper.modify(itripHotelOrder);
    }

    public Integer removeById(Long id) throws Exception {
        return itripHotelOrderMapper.removeById(id);
    }

    public Page<List<ItripHotelOrder>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = itripHotelOrderMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelOrder> itripHotelOrderList = itripHotelOrderMapper.getListByMap(param);
        page.setRows(itripHotelOrderList);
        return page;
    }

    @Override
    public BigDecimal getOrderPayAmount(int count, Long roomId) throws Exception {
        BigDecimal roomPrice = itripHotelRoomMapper.getById(roomId).getRoomPrice();
        BigDecimal payAmount = BigDecimalUtil.OperationASMD(count, roomPrice, BigDecimalUtil.BigDecimalOprations.multiply, 2, ROUND_DOWN);
        return payAmount;
    }

    @Override
    public Map<String, Object> addItripOrderAsLinkUser(ItripHotelOrder itripHotelOrder, List<ItripUserLinkUser> itripUserLinkUsers) throws Exception {
        Map<String, Object> map = new HashMap<>();
        itripHotelOrder.setCreationDate(new Date());
        int flag = 0;
        Integer save = itripHotelOrderMapper.save(itripHotelOrder);
        if (save > 0){
            Long orderId = itripHotelOrder.getId();
            for (ItripUserLinkUser itripUserLinkUser : itripUserLinkUsers) {
                ItripOrderLinkUser itripOrderLinkUser = new ItripOrderLinkUser();
                itripOrderLinkUser.setOrderId(orderId);
                itripOrderLinkUser.setLinkUserId(itripUserLinkUser.getId());
                itripOrderLinkUser.setLinkUserName(itripUserLinkUser.getLinkUserName());
                itripOrderLinkUser.setCreatedBy(itripHotelOrder.getCreatedBy());
                itripOrderLinkUser.setCreationDate(new Date());
                itripOrderLinkUserMapper.save(itripOrderLinkUser);
            }
//            ItripTradeEnds itripTradeEnds = new ItripTradeEnds();
//            itripTradeEnds.setFlag("0");
//            itripTradeEnds.setOrderNo(itripHotelOrder.getOrderNo());
//            itripTradeEndsMapper.save(itripTradeEnds);
        }
        map.put("id",itripHotelOrder.getId().toString());
        map.put("orderNo",itripHotelOrder.getOrderNo());
        return map;
    }

}
