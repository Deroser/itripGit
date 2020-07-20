package com.kgc.service;

import com.kgc.beans.model.ItripHotelOrder;
/**
 * OrderService
 * 李文俊
 * 2020.7.20
 */
public interface OrderService {
    public ItripHotelOrder loadItripHotelOrder(String orderNo)throws Exception;

    public void paySuccess(String orderNo,int payType,String tradeNo)throws Exception;

    public void payFailed(String orderNo,int payType,String tradeNo)throws Exception;

    public boolean processed(String orderNo)throws Exception;
}
