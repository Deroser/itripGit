package com.kgc.service.impl;

import com.kgc.service.TokenService;
import com.kgc.utils.MD5;
import com.kgc.utils.RedisAPI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * TokenServiceImpl
 * 李文俊
 * 2020.7.20
 */
@Service("biztokenService")
public class TokenServiceImpl implements TokenService {
    private Logger logger = Logger.getLogger(TokenServiceImpl.class);
    @Autowired
    private RedisAPI redisAPI;//过期时间

    public boolean exists(String token){
        return redisAPI.exist(token);
    }
    /**
     * 验证token是否有效
     */
    @Override
    public boolean validates(String agent,String token){
        System.out.println(token);
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

}
