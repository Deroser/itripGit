package com.kgc.service;

import com.kgc.beans.model.ItripHotelOrder;

public interface OrderService {
    public ItripHotelOrder loadItripHotelOrder(String orderNo)throws Exception;
}
