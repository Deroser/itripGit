package com.kgc.service;

import com.kgc.beans.model.ItripUser;

public interface TokenService {
    /**
     * 会话超时时间
     */
    public final static int SESSION_TIMEOUT = 2*60*60;//默认两个小时
    /**
     * 置换保护时间
     */
    public final static int REPLACEMENT_PROTECTION_TIMEOUT = 60*60;//默认一个小时

    /**
     * 生成Token
     * PC 端：token:PC-USERCODE[加密]-USERID-CREATIONDATE-RONDEM[6 位]
     * 移动端：token: MOBILE-USERCODE[加密]-USERID-CREATIONDATE-RONDEM[6 位]
     */
    public String generateToken(String agent , ItripUser user);

    /**
     * 保存信息到Redis
     */
    public void save(String token,ItripUser user);

    /**
     * 获取用户信息
     */
    public ItripUser load(String token);

    /**
     * 删除用户信息
     */
    public void delete(String token);
    /**
     * 验证token是否有效
     */
    public boolean validate(String agent,String token);

    /**
     * 置换token
     * 1,首先判断token是否有效
     * 2，生成token后的一个小时内不允许置换
     * 3，置换token，需要生成一个新的token，并且旧token不能立即失效，应设置为置换后时间延长两分钟
     * 4，兼容手机端和pc端
     */
    public String replaceToken(String agent,String token)throws Exception;
    /**
     * 旧token延迟时间
     */
    public final static int REPLACEMENT_DELAY = 2*60;
}
