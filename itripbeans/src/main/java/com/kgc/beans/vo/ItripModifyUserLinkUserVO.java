package com.kgc.beans.vo;

public class ItripModifyUserLinkUserVO {

    private Long id;
    private String linkIdCard;
    private int linkIdCardType;
    private String linkPhone;
    private String linkUserName;
    private Integer userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinkIdCard() {
        return linkIdCard;
    }

    public void setLinkIdCard(String linkIdCard) {
        this.linkIdCard = linkIdCard;
    }

    public int getLinkIdCardType() {
        return linkIdCardType;
    }

    public void setLinkIdCardType(int linkIdCardType) {
        this.linkIdCardType = linkIdCardType;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLinkUserName() {
        return linkUserName;
    }

    public void setLinkUserName(String linkUserName) {
        this.linkUserName = linkUserName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
