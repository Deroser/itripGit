package com.kgc.beans.vo;

import java.util.List;
/**
 * BizVideoDescVo
 * 李文俊
 * 2020.7.20
 */
//酒店特色、商圈、酒店名称
public class BizVideoDescVo {
    private List<String > hotelFeatureList;
    private String hotelName;
    private List<String > tradingAreaNameList;

    public List<String> getHotelFeatureList() {
        return hotelFeatureList;
    }

    public void setHotelFeatureList(List<String> hotelFeatureList) {
        this.hotelFeatureList = hotelFeatureList;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<String> getTradingAreaNameList() {
        return tradingAreaNameList;
    }

    public void setTradingAreaNameList(List<String> tradingAreaNameList) {
        this.tradingAreaNameList = tradingAreaNameList;
    }
}
