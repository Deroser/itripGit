package com.kgc.utils;
/**
 * itripException
 * 李文俊
 * 2020.7.20
 */
public class itripException extends Exception{
    private String errorCode;

    public itripException(String errorCode) {
        this.errorCode = errorCode;
    }

    public itripException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public itripException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public itripException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public itripException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
