package com.kgc.beans.vo;

/**
 * Created by ASUS on 2018/7/23.
 */
public class SearchHotCityVO {
    //城市Id
    private Integer cityId;
    //数目
    private Integer count;

    public SearchHotCityVO() {
    }

    public SearchHotCityVO(Integer cityId, Integer count) {
        this.cityId = cityId;
        this.count = count;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
