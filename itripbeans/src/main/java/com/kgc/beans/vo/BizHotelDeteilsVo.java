package com.kgc.beans.vo;

import com.kgc.beans.model.ItripBizHotelDeteil;

import java.util.ArrayList;
import java.util.List;

public class BizHotelDeteilsVo {
    private String description;
    private String name = "酒店介绍";
    List<ItripBizHotelDeteil> list;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<ItripBizHotelDeteil> getList() {
        return list;
    }

    public void setList(List<ItripBizHotelDeteil> list) {
        this.list = list;
    }


}
