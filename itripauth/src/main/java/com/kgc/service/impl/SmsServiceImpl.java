package com.kgc.service.impl;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.kgc.service.SmsService;
import com.kgc.utils.SystemConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Set;

/**
 * SmsServiceImpl
 * 李文俊
 * 2020.7.20
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {
    private Logger logger = Logger.getLogger(SmsServiceImpl.class);
    @Resource
    private SystemConfig systemConfig;
    @Override
    public void send(String to, String templateId, String[] datas) throws Exception {
        HashMap<String,Object > result = null;
        CCPRestSmsSDK restApi = new CCPRestSmsSDK();
        restApi.init(systemConfig.getSmsServerIP(),systemConfig.getSmsServerPort());
        restApi.setAccount(systemConfig.getSmsAccountSid(),systemConfig.getSmsAuthToken());
        restApi.setAppId(systemConfig.getSmsAppID());
        result = restApi.sendTemplateSMS(to,templateId,datas);
        System.out.println("SDKTestGetSubAccounts result="+result);
        String resultstate = (String)result.get("statusCode");
        if ("000000".equals(resultstate)) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码="+result.get("statusCode")+"错误信息="+result.get("statusMsg"));
            logger.error("错误码="+result.get("statusCode")+"错误信息="+result.get("statusMsg"));
            throw new Exception("错误码="+result.get("statusCode")+"错误信息="+result.get("statusMsg"));

        }

    }
}
