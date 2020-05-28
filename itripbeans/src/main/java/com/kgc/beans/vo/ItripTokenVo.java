package com.kgc.beans.vo;

public class ItripTokenVo {
    //过期时间
    private Long expTime;
    //生成时间
    private Long genTime;
    //token字符串
    private String token;

    public ItripTokenVo() {
    }

    public ItripTokenVo(Long expTime, Long genTime, String token) {
        this.expTime = expTime;
        this.genTime = genTime;
        this.token = token;
    }

    public Long getExpTime() {
        return expTime;
    }

    public void setExpTime(Long expTime) {
        this.expTime = expTime;
    }

    public Long getGenTime() {
        return genTime;
    }

    public void setGenTime(Long genTime) {
        this.genTime = genTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
