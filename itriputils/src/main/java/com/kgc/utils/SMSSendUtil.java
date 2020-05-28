package com.kgc.utils;


import java.util.HashMap;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class SMSSendUtil {
    public boolean sendSms(String phonenum, String b, String str[]) {
        HashMap<String, Object> result = null;
        boolean ok = false;
        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        //沙盒环境
        restAPI.init("sandboxapp.cloopen.com", "8883");
        //生产环境
        restAPI.init("app.cloopen.com", "8883");



        restAPI.setAccount("8a216da871bf71a10171f23696f61083", "d2f9b625894140d5807b622e5258603e");


        restAPI.setAppId("8a216da871bf71a10171f23697c61089");

        result = restAPI.sendTemplateSMS(phonenum, b, str);

        System.out.println("SDKTestGetSubAccounts result=" + result);
        String resultstate = (String) result.get("statusCode");

        if ("000000".equals(resultstate)) {
// //正常返回输出data包体信息（map）
// HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
// Set<String> keySet = data.keySet();
// for(String key:keySet){
// Object object = data.get(key);
// System.out.println(key +" = "+object);
// }
            ok = true;
        } else {
//异常返回输出错误码和错误信息
            //log.info("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            ok = false;
        }
        return ok;
    }


}
