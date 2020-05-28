package com.kgc.beans.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ZhangJiTao on 2018-04-27.
 */

public class SearchHotelVO {
    //[非必填] 按照某个字段升序,取值为(isOkCount或avgScore或minPrice或hotelLevel)
    private String ascSort;

    //[非必填] 入住日期
    private String checkInDate;

    //[非必填] 退房日期
    private String checkOutDate;

//[非必填] 按照某个字段降序,取值为(isOkCount或avgScore或minPrice或hotelLevel)
    private String descSort;

   //[必填] 目的地
    private String destination;

   //[非必填] 酒店特色id
    private String featureIds;
 //[非必填] 酒店级别(1-5)
    private Integer hotelLevel;

    //[非必填] 关键词
    private String keywords;
    // [非必填] 最高价
    private Double maxPrice;
   //[非必填] 最低价
    private Double minPrice;

    //[必填] 当前页数
    private Integer pageNo;
    //[必填] 每页多少条
    private Integer pageSize;

  //[非必填] 商圈id
    private String tradeAreaIds;

    public String getAscSort() {
        return ascSort;
    }

    public void setAscSort(String ascSort) {
        this.ascSort = ascSort;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getDescSort() {
        return descSort;
    }

    public void setDescSort(String descSort) {
        this.descSort = descSort;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFeatureIds() {
        return featureIds;
    }

    public void setFeatureIds(String featureIds) {
        this.featureIds = featureIds;
    }

    public Integer getHotelLevel() {
        return hotelLevel;
    }

    public void setHotelLevel(Integer hotelLevel) {
        this.hotelLevel = hotelLevel;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getTradeAreaIds() {
        return tradeAreaIds;
    }

    public void setTradeAreaIds(String tradeAreaIds) {
        this.tradeAreaIds = tradeAreaIds;
    }
}
