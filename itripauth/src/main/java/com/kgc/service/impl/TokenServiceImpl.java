package com.kgc.service.impl;

import com.alibaba.fastjson.JSON;
import com.kgc.beans.model.ItripUser;
import com.kgc.exception.TokenValidationFailedException;
import com.kgc.service.TokenService;
import com.kgc.utils.MD5;
import com.kgc.utils.RedisAPI;
import com.kgc.utils.UserAgentUtil;
import cz.mallat.uasparser.UserAgentInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service("TokenService")
public class TokenServiceImpl implements TokenService {
    private Logger logger = Logger.getLogger(SmsServiceImpl.class);
    @Autowired
    private RedisAPI redisAPI;//过期时间
    private int expire = SESSION_TIMEOUT;
    private String tokenPrefix = "token:";

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    public String generateToken(String agent, ItripUser user) {
        StringBuffer sb = new StringBuffer();
        try {
            //判断是否是移动端
            UserAgentInfo userAgentInfo = UserAgentUtil.getUasParser().parse(agent);
            sb.append(tokenPrefix);
            if (userAgentInfo.getDeviceType().equals(UserAgentInfo.UNKNOWN)){
                if (UserAgentUtil.CheckAgent(agent)){
                    sb.append("MOBILE-");
                }else {
                    sb.append("PC-");
                }
            }else if (userAgentInfo.getDeviceType().equals("Personal computer")){
                sb.append("PC-");
            }else {
                sb.append("MOBILE-");
            }
            sb.append(com.kgc.utils.MD5.getMd5(user.getUserCode(),32)+"-");
            sb.append(user.getId()+"-");
            sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"-");
            sb.append(com.kgc.utils.MD5.getMd5(agent,6));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void save(String token, ItripUser user) {
        if (token.startsWith(tokenPrefix+"PC-")){
            redisAPI.set(token,expire,JSON.toJSONString(user));
        }else {
            redisAPI.set(token,JSON.toJSONString(user));
        }
    }

    @Override
    public ItripUser load(String token) {
        return null;
    }

    @Override
    public void delete(String token) {
        redisAPI.delete(token);
    }
    public boolean exists(String token){
        return redisAPI.exist(token);
    }
    /**
     * 验证token是否有效
     */
    @Override
    public boolean validate(String agent,String token){
        if (!exists(token)){//token不存在
            return false;
        }
        try {
            Date tokenGenTime;//Token生成时间
            String agentMd5;
            String[] tokenDeteils = token.split("-");
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            tokenGenTime = format.parse(tokenDeteils[3]);
            long passed = Calendar.getInstance().getTimeInMillis() - tokenGenTime.getTime();
            if (passed > this.SESSION_TIMEOUT*1000) {
                return false;
            }else {
                agentMd5 = tokenDeteils[4];
                if (MD5.getMd5(agent,6).equals(agentMd5)){
                    return true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    /**
     * 置换token
     * 1,首先判断token是否有效
     * 2，生成token后的一个小时内不允许置换
     * 3，置换token，需要生成一个新的token，并且旧token不能立即失效，应设置为置换后时间延长两分钟
     * 4，兼容手机端和pc端
     */
    @Override
    public String replaceToken(String agent, String token) throws TokenValidationFailedException {
        //1,首先判断token是否有效
        if (!exists(token)){
            //终止置换
            throw new TokenValidationFailedException("未知的Token或Token已过期");
        }
        Date tokenGenTime;//生成时间
        try{
            //2，生成token后的一个小时内不允许置换
            String[] tokenDetails = token.split("-");
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            tokenGenTime = format.parse(tokenDetails[3]);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error(e);
            throw new TokenValidationFailedException("token格式错误"+token);
        }
        long passed = Calendar.getInstance().getTimeInMillis()-tokenGenTime.getTime();
        if (passed < REPLACEMENT_PROTECTION_TIMEOUT * 1000){
            throw new TokenValidationFailedException("token处于置换保护时间，剩余"+
                    (REPLACEMENT_PROTECTION_TIMEOUT)*1000+"(s),禁止置换");
        }
//        3，置换token，需要生成一个新的token，并且旧token不能立即失效，应设置为置换后时间延长两分钟
        String newToken = "";
        ItripUser itripUser = this.load(token);
        long ttl = redisAPI.ttl(token);//token有效时期
        //4,兼容手机端和pc端
        if (ttl > 0 || ttl == -1){
            newToken = this.generateToken(agent,itripUser);
            this.save(newToken,itripUser);
            redisAPI.set(token,REPLACEMENT_DELAY,JSON.toJSONString(itripUser));
        }else {
            throw new TokenValidationFailedException("当前token的过期时间异常，禁止置换");
        }
        return newToken;
    }
}
