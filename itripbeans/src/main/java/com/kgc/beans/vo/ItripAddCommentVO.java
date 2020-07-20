package com.kgc.beans.vo;

import com.kgc.beans.model.ItripImage;
/**
 * ItripAddCommentVO
 * 李文俊
 * 2020.7.20
 */
public class ItripAddCommentVO {

    private Long hotelId;
    private Long orderId;
    private Long productId;
    private Integer productType;
    private Integer isHavingImg;
    private Integer positionScore;
    private Integer facilitiesScore;
    private Integer serviceScore;
    private Integer hygieneScore;
    private Long tripMode;
    private Integer isOk;
    private String content;
    private ItripImage[] itripImages;


    public Long getOrderId() {
    return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public ItripImage[] getItripImages() {
        return itripImages;
    }

    public void setItripImages(ItripImage[] itripImages) {
        this.itripImages = itripImages;
    }

    public Long getHotelId() {
        return hotelId;
    }
    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getIsHavingImg() {
        return isHavingImg;
    }

    public void setIsHavingImg(Integer isHavingImg) {
        this.isHavingImg = isHavingImg;
    }

    public Integer getPositionScore() {
        return positionScore;
    }

    public void setPositionScore(Integer positionScore) {
        this.positionScore = positionScore;
    }

    public Integer getFacilitiesScore() {
        return facilitiesScore;
    }

    public void setFacilitiesScore(Integer facilitiesScore) {
        this.facilitiesScore = facilitiesScore;
    }

    public Integer getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Integer serviceScore) {
        this.serviceScore = serviceScore;
    }

    public Integer getHygieneScore() {
        return hygieneScore;
    }

    public void setHygieneScore(Integer hygieneScore) {
        this.hygieneScore = hygieneScore;
    }

    public Long getTripMode() {
        return tripMode;
    }

    public void setTripMode(Long tripMode) {
        this.tripMode = tripMode;
    }

    public Integer getIsOk() {
        return isOk;
    }

    public void setIsOk(Integer isOk) {
        this.isOk = isOk;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
