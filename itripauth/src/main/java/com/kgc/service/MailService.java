package com.kgc.service;

public interface MailService {
    public void sendActivationMail(String recipientAddress,int code)throws Exception;
}
