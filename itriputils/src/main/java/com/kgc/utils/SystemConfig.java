package com.kgc.utils;

public class SystemConfig {
    /**
     * 短信平台账号 ACCOUNT SID
     */
    private String smsAccountSid;
    /**
     * 短信平台账号 AUTH TOKEN
     */
    private String smsAuthToken;
    /**
     * 短信平台账号 APP ID
     */
    private String smsAppID;
    /**
     * 短信平台账号 serverIp
     */
    private String smsServerIP;
    /**
     * 短信平台账号 serverPort
     */
    private String smsServerPort;
    //发件人地址
    private String senderAddress;
    //发件人账户名
    private String senderAccount;
    //授权码
    private String senderPassword;

    private String fileUploadPathString;

    private String visitImgUrlString;

    private String machineCode;

    private String orderProcessOK;

    private String orderProcessCancel;

    private String tradeEndUrl;

    private boolean tradeUseProxy;

    private String tradeProxyHost;

    private Integer tradeProxyPort;

    public String getTradeEndUrl() {
        return tradeEndUrl;
    }

    public void setTradeEndUrl(String tradeEndUrl) {
        this.tradeEndUrl = tradeEndUrl;
    }

    public boolean getTradeUseProxy() {
        return tradeUseProxy;
    }

    public void setTradeUseProxy(boolean tradeUseProxy) {
        this.tradeUseProxy = tradeUseProxy;
    }

    public String getTradeProxyHost() {
        return tradeProxyHost;
    }

    public void setTradeProxyHost(String tradeProxyHost) {
        this.tradeProxyHost = tradeProxyHost;
    }

    public Integer getTradeProxyPort() {
        return tradeProxyPort;
    }

    public void setTradeProxyPort(Integer tradeProxyPort) {
        this.tradeProxyPort = tradeProxyPort;
    }

    public String getFileUploadPathString() {
        return fileUploadPathString;
    }

    public void setFileUploadPathString(String fileUploadPathString) {
        this.fileUploadPathString = fileUploadPathString;
    }

    public String getVisitImgUrlString() {
        return visitImgUrlString;
    }

    public void setVisitImgUrlString(String visitImgUrlString) {
        this.visitImgUrlString = visitImgUrlString;
    }

    public String getOrderProcessOK() {
        return orderProcessOK;
    }

    public void setOrderProcessOK(String orderProcessOK) {
        this.orderProcessOK = orderProcessOK;
    }

    public String getOrderProcessCancel() {
        return orderProcessCancel;
    }

    public void setOrderProcessCancel(String orderProcessCancel) {
        this.orderProcessCancel = orderProcessCancel;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public String getSmsAccountSid() {
        return smsAccountSid;
    }

    public void setSmsAccountSid(String smsAccountSid) {
        this.smsAccountSid = smsAccountSid;
    }

    public String getSmsAuthToken() {
        return smsAuthToken;
    }

    public void setSmsAuthToken(String smsAuthToken) {
        this.smsAuthToken = smsAuthToken;
    }

    public String getSmsAppID() {
        return smsAppID;
    }

    public void setSmsAppID(String smsAppID) {
        this.smsAppID = smsAppID;
    }

    public String getSmsServerIP() {
        return smsServerIP;
    }

    public void setSmsServerIP(String smsServerIP) {
        this.smsServerIP = smsServerIP;
    }

    public String getSmsServerPort() {
        return smsServerPort;
    }

    public void setSmsServerPort(String smsServerPort) {
        this.smsServerPort = smsServerPort;
    }
}
