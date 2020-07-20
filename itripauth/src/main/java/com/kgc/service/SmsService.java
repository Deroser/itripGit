package com.kgc.service;
/**
 * SmsService
 * 李文俊
 * 2020.7.20
 */
public interface SmsService {
    /**
     * 发送短信
     * @param to 要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号
     * @param templateId 模板ID 测试时可以用1（默认）
     * @param datas 替换内容
     * @throws Exception
     */
    public void send(String to,String templateId,String[] datas)throws Exception;
}
