package com.kgc.service;
/**
 * MailService
 * 李文俊
 * 2020.7.20
 */
public interface MailService {
    public void sendActivationMail(String recipientAddress,int code)throws Exception;
}
