package com.kgc.service;

import com.kgc.beans.vo.BizVideoDescVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
/**
 * ItripVideoDescService
 * 李文俊
 * 2020.7.20
 */
public interface ItripVideoDescService {
    public BizVideoDescVo getVideoDescByHotelId(String  hotelId)throws Exception;

}
