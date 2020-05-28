package com.kgc.beans.model;

import java.util.Date;

public class ItripCommentData {
    private Date checkInDate;
    private String content;
    private Date creationDate;
    private Integer hotelLevel;
    private Long id;
    private Integer isHavingImg;
    private String roomTitle;
    private Integer score;
    private String tripModeName;
    private String userCode;

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getHotelLevel() {
        return hotelLevel;
    }

    public void setHotelLevel(Integer hotelLevel) {
        this.hotelLevel = hotelLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsHavingImg() {
        return isHavingImg;
    }

    public void setIsHavingImg(Integer isHavingImg) {
        this.isHavingImg = isHavingImg;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTripModeName() {
        return tripModeName;
    }

    public void setTripModeName(String tripModeName) {
        this.tripModeName = tripModeName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
