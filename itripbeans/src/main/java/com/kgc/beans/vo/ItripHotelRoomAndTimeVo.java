package com.kgc.beans.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ItripHotelRoom表
 */
public class ItripHotelRoomAndTimeVo implements Serializable {

    //主键
    private Long id;
    //酒店ID
    private Long hotelId;
    //房间名称
    private String roomTitle;
    //房间价格
    private BigDecimal roomPrice;
    //酒店床型
    private Long roomBedTypeId;
    //是否包含早餐
    private Integer isHavingBreakfast;
    //1:在线付 2:到店付 3:不限
    private Integer payType;
    //满意度（冗余字段，在用户评论后更新）
    private BigDecimal satisfaction;
    //是否可预订(0:不可以 1:可以)
    private Integer isBook;
    //是否可取消(0:不可 1:可以)
    private Integer isCancel;
    //是否及时响应(0:不及时 1:及时)
    private Integer isTimelyResponse;
    //
    private Date creationDate;
    //
    private Long createdBy;
    //
    private Date modifyDate;
    //
    private Long modifiedBy;

    private Date endDate;

    private Date startDate;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getHotelId() {
        return this.hotelId;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomTitle() {
        return this.roomTitle;
    }

    public void setRoomPrice(BigDecimal roomPrice) {
        this.roomPrice = roomPrice;
    }

    public BigDecimal getRoomPrice() {
        return this.roomPrice;
    }

    public void setRoomBedTypeId(Long roomBedTypeId) {
        this.roomBedTypeId = roomBedTypeId;
    }

    public Long getRoomBedTypeId() {
        return this.roomBedTypeId;
    }

    public void setIsHavingBreakfast(Integer isHavingBreakfast) {
        this.isHavingBreakfast = isHavingBreakfast;
    }

    public Integer getIsHavingBreakfast() {
        return this.isHavingBreakfast;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPayType() {
        return this.payType;
    }

    public void setSatisfaction(BigDecimal satisfaction) {
        this.satisfaction = satisfaction;
    }

    public BigDecimal getSatisfaction() {
        return this.satisfaction;
    }

    public void setIsBook(Integer isBook) {
        this.isBook = isBook;
    }

    public Integer getIsBook() {
        return this.isBook;
    }

    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    public Integer getIsCancel() {
        return this.isCancel;
    }

    public void setIsTimelyResponse(Integer isTimelyResponse) {
        this.isTimelyResponse = isTimelyResponse;
    }

    public Integer getIsTimelyResponse() {
        return this.isTimelyResponse;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Date getModifyDate() {
        return this.modifyDate;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }
}
