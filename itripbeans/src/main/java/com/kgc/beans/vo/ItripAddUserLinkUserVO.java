package com.kgc.beans.vo;


import org.codehaus.jackson.annotate.JsonProperty;

/**
 * ItripAddUserLinkUserVO
 * 李文俊
 * 2020.7.20
 */
public class ItripAddUserLinkUserVO {

    private String linkIdCard;
    private Integer linkIdCardType;
    private String linkPhone;
    private String linkUserName;
    private Integer userId;

    public String getLinkIdCard() {
        return linkIdCard;
    }
    @JsonProperty(value = "linkIdCard")
    public void setLinkIdCard(String linkIdCard) {
        this.linkIdCard = linkIdCard;
    }

    public int getLinkIdCardType() {
        return linkIdCardType;
    }
    @JsonProperty(value = "linkIdCardType")
    public void setLinkIdCardType(Integer linkIdCardType) {
        this.linkIdCardType = linkIdCardType;
    }

    public String getLinkPhone() {
        return linkPhone;
    }
    @JsonProperty(value = "linkPhone")
    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLinkUserName() {
        return linkUserName;
    }
    @JsonProperty(value = "linkUserName")
    public void setLinkUserName(String linkUserName) {
        this.linkUserName = linkUserName;
    }

    public int getUserId() {
        return userId;
    }
    @JsonProperty(value = "userId")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
