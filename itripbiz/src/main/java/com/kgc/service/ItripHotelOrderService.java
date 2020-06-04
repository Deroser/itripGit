package com.kgc.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.kgc.beans.model.ItripHotelOrder;
import com.kgc.beans.model.ItripOrderLinkUser;
import com.kgc.beans.model.ItripUserLinkUser;
import com.kgc.beans.vo.ItripHotelOrderVo;
import com.kgc.beans.vo.ItripPersonalOrderRoomVO;
import com.kgc.beans.vo.ItripPersonalOrderVo;
import com.kgc.beans.vo.ItripSearchOrderVO;
import com.kgc.utils.Page;


public interface ItripHotelOrderService {

    public ItripHotelOrder getById(Long id)throws Exception;

    public ItripHotelOrderVo getQueryOrderById(Long id)throws Exception;

    public ItripPersonalOrderRoomVO getPersonalOrderRoomInfoByOrderid(Long id)throws Exception;

    public List<ItripPersonalOrderRoomVO> getPersonalOrderRoomInfoByHotelid(Long id)throws Exception;

    public List<ItripHotelOrder>	getListByMap(Map<String, Object> param)throws Exception;

    public List<ItripPersonalOrderVo>getPersonaLorderListByMap(ItripSearchOrderVO vo)throws Exception;

    public Integer getCountByMap(Map<String, Object> param)throws Exception;

    public Integer getPersonaLorderCountByMap(ItripSearchOrderVO vo)throws Exception;

    public Integer save(ItripHotelOrder itripHotelOrder)throws Exception;

    public Integer modify(ItripHotelOrder itripHotelOrder)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public Page<List<ItripHotelOrder>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;

    //根据订单的预定天数和价格计算订单总额
    public BigDecimal getOrderPayAmount(int count,Long roomId)throws Exception;

    //添加订单数据
    public Map<String ,Object> addItripOrderAsLinkUser(ItripHotelOrder itripHotelOrder, List<ItripUserLinkUser> itripUserLinkUsers)throws Exception;

    public boolean flushOrderStatus(Integer type)throws Exception;
}
