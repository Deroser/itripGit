package com.kgc.exception;

/**
 * 用户登录异常
 */
public class UserLoginFailedException extends Exception {
    public UserLoginFailedException(String msg){
        super(msg);
    }
}
