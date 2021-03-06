package com.kgc.beans.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * ItripHotelOrder表
 * 李文俊
 * 2020.7.20
 */
public class ItripHotelOrderVo implements Serializable {

    //主键
    private Long id;
    //订单类型(0:旅游产品 1:酒店产品 2：机票产品)
    private Integer orderType;
    //订单号
    private String orderNo;
    //冗余字段 酒店id
    private Long hotelId;
    //冗余字段 酒店名称
    private String hotelName;
    //房间id
    private Long roomId;
    //消耗数量
    private Integer count;
    //预订天数
    private Integer bookingDays;
    //入住时间
    private Date checkInDate;
    //退房时间
    private Date checkOutDate;
    //支付方式:1:支付宝 2:微信 3:到店付
    private Integer payType;
    //联系手机号
    private String noticePhone;
    //联系邮箱
    private String noticeEmail;
    //特殊需求
    private String specialRequirement;
    //是否需要发票（0：不需要 1：需要）
    private Integer isNeedInvoice;
    //发票类型（0：个人 1：公司）
    private Integer invoiceType;
    //发票抬头
    private String invoiceHead;
    //入住人名称
    private String linkUserName;
    //0:WEB端 1:手机端 2:其他客户端
    private Integer bookType;

    private List<ItripOrderLinkUserVo> itripOrderLinkUserList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBookingDays() {
        return bookingDays;
    }

    public void setBookingDays(Integer bookingDays) {
        this.bookingDays = bookingDays;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getNoticePhone() {
        return noticePhone;
    }

    public void setNoticePhone(String noticePhone) {
        this.noticePhone = noticePhone;
    }

    public String getNoticeEmail() {
        return noticeEmail;
    }

    public void setNoticeEmail(String noticeEmail) {
        this.noticeEmail = noticeEmail;
    }

    public String getSpecialRequirement() {
        return specialRequirement;
    }

    public void setSpecialRequirement(String specialRequirement) {
        this.specialRequirement = specialRequirement;
    }

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }

    public String getLinkUserName() {
        return linkUserName;
    }

    public void setLinkUserName(String linkUserName) {
        this.linkUserName = linkUserName;
    }

    public Integer getBookType() {
        return bookType;
    }

    public void setBookType(Integer bookType) {
        this.bookType = bookType;
    }

    public List<ItripOrderLinkUserVo> getItripOrderLinkUserList() {
        return itripOrderLinkUserList;
    }

    public void setItripOrderLinkUserList(List<ItripOrderLinkUserVo> itripOrderLinkUserList) {
        this.itripOrderLinkUserList = itripOrderLinkUserList;
    }
}
