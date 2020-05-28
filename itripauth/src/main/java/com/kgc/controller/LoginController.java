package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kgc.beans.model.ItripUser;
import com.kgc.beans.vo.ItripTokenVo;
import com.kgc.exception.UserLoginFailedException;
import com.kgc.service.ItripUserService;
import com.kgc.service.TokenService;
import com.kgc.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * 用户登录控制器
 */
@Controller
@RequestMapping(value = "/api")
public class LoginController {
    @Resource
    private ItripUserService itripUserService;
    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     * @param name
     * @param pwd
     * @param request
     * @return
     */
    @RequestMapping(value = "/dologin",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto dologin(@RequestParam(value = "name") String name,
                       @RequestParam(value = "password") String pwd,
                       HttpServletRequest request){
        if(EmptyUtils.isNotEmpty(name) && EmptyUtils.isNotEmpty(pwd)){
            ItripUser user = null;
            try {
                user = itripUserService.login(name.trim(), MD5.getMd5(pwd.trim(),32));
            } catch (UserLoginFailedException e){
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_AUTHENTICATION_FAILED);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(),ErrorCode.ERROR);
            }
            if (EmptyUtils.isNotEmpty(user)) {
                String token = tokenService.generateToken(request.getHeader("user-agent"), user);
                tokenService.save(token,user);
                //返回前端需要的数据
                ItripTokenVo itripTokenVo = new ItripTokenVo(Calendar.getInstance().getTimeInMillis()+TokenService.SESSION_TIMEOUT*1000,
                        Calendar.getInstance().getTimeInMillis(),token);
                return DtoUtil.returnDataSuccess(itripTokenVo);
            }else {
                return DtoUtil.returnFail("用户名密码错误", ErrorCode.AUTH_PARAMETER_ERROR);
            }
        }else {
            return DtoUtil.returnFail("参数错误", ErrorCode.AUTH_PARAMETER_ERROR);
        }

    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto logout(HttpServletRequest request){
        //验证token
        String token = request.getHeader("token");
        if (! tokenService.validate(request.getHeader("user-agent"),token)){
            return DtoUtil.returnFail("token无效",ErrorCode.AUTH_TOKEN_INVALID);
        }else {
            try {
                tokenService.delete(token);
                return DtoUtil.returnDataSuccess("注销成功");
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("注销失败",ErrorCode.AUTH_UNKNOWN);
            }
        }
    }


}
