package com.kgc.beans.vo;
/**
 * BizCityIdAndNameVo
 * 李文俊
 * 2020.7.20
 */
public class BizCityIdAndNameVo {
    private Long id;
    private String name;

    public BizCityIdAndNameVo() {
    }

    public BizCityIdAndNameVo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
