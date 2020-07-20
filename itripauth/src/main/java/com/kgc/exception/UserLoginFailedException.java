package com.kgc.exception;
/**
 * UserLoginFailedException
 * 李文俊
 * 2020.7.20
 */
/**
 * 用户登录异常
 */
public class UserLoginFailedException extends Exception {
    public UserLoginFailedException(String msg){
        super(msg);
    }
}
