package com.kgc.service;


public interface TokenService {
    /**
     * 会话超时时间
     */
    public final static int SESSION_TIMEOUT = 2*60*60;//默认两个小时
    /**
     * 验证token是否有效
     */
    public boolean validates(String agent, String token);

}
