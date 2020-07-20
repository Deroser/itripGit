package com.kgc.beans.vo;
/**
 * ItripUserVo
 * 李文俊
 * 2020.7.20
 */
public class ItripUserVo {

    /**
     * userCode : string
     * userName : string
     * userPassword : string
     */

    private String userCode;
    private String userName;
    private String userPassword;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
