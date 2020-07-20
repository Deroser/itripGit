package com.kgc.controller;

import com.kgc.beans.vo.ItripTokenVo;
import com.kgc.service.TokenService;
import com.kgc.utils.Dto;
import com.kgc.utils.DtoUtil;
import com.kgc.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
/**
 * TokenController
 * 李文俊
 * 2020.7.20
 */
@Controller
@RequestMapping(value = "/api")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/retoken",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto retoken(HttpServletRequest request){
        String agent = request.getHeader("user-agent");
        String token = request.getHeader("token");
        try {
            String newToken = tokenService.replaceToken(agent, token);
            ItripTokenVo itripTokenVo = new ItripTokenVo(Calendar.getInstance().getTimeInMillis()+TokenService.SESSION_TIMEOUT*1000,
                    Calendar.getInstance().getTimeInMillis(),newToken);
            return DtoUtil.returnDataSuccess(itripTokenVo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_AUTHENTICATION_FAILED);
        }
    }
}
