package com.kgc.controller;

import com.kgc.beans.model.ItripUser;
import com.kgc.beans.vo.ItripUserVo;
import com.kgc.service.ItripUserService;
import com.kgc.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    private ItripUserService itripUserService;

    /**
     * 手机号注册
     * @param itripUserVo
     * @return
     */
    @RequestMapping(value = "/registerbyphone",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto registerbyphone(@RequestBody ItripUserVo itripUserVo){
        try {
            if (!validPhone(itripUserVo.getUserCode()))
                return DtoUtil.returnFail("请使用正确的手机号",ErrorCode.AUTH_ILLEGAL_USERCODE);
            ItripUser itripUser = new ItripUser();
            BeanUtils.copyProperties(itripUserVo,itripUser);
            itripUser.setUserType(0);
            itripUser.setActivated(0);
            if (EmptyUtils.isEmpty(itripUserService.findByUserCode(itripUserVo.getUserCode()))){
                itripUser.setUserPassword(MD5.getMd5(itripUserVo.getUserPassword(),32));
                itripUserService.itripCreateUserByPhone(itripUser);
                return  DtoUtil.returnSuccess();
            }else {
                return DtoUtil.returnFail("用户存在，注册失败",ErrorCode.AUTH_USER_ALREADY_EXISTS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * 验证手机号格式
     * @param phone
     * @return
     */
    private boolean validPhone(String phone){
        String regex = "^1[3578]{1}\\d{9}$";
        return Pattern.compile(regex).matcher(phone).find();
    }

    /**
     * 验证邮箱格式
     */
    private boolean validEmail(String email){
        String regex = "^[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*\\.[a-z]{2,}$";
        return Pattern.compile(regex).matcher(email).find();
    }

    /**
     * 验证用户名是否存在
     */
    @RequestMapping(value = "/ckusr",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto ckusr(@RequestParam("name") String name){
        try {
            if (EmptyUtils.isEmpty(itripUserService.findByUserCode(name))){
                return DtoUtil.returnSuccess("用户名可用");
            }else{
                return DtoUtil.returnFail("用户名已存在",ErrorCode.AUTH_USER_ALREADY_EXISTS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/doregister",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto doregister(@RequestBody ItripUserVo itripUserVo){
        try {
            if (!validEmail(itripUserVo.getUserCode()))
                return DtoUtil.returnFail("请使用正确的邮箱",ErrorCode.AUTH_ILLEGAL_USERCODE);
            ItripUser itripUser = new ItripUser();
            BeanUtils.copyProperties(itripUserVo,itripUser);
            itripUser.setUserType(0);
            itripUser.setActivated(0);
            if (EmptyUtils.isEmpty(itripUserService.findByUserCode(itripUserVo.getUserCode()))){
                itripUser.setUserPassword(MD5.getMd5(itripUserVo.getUserPassword(),32));
//                itripUserService.itripCreateUserByPhone(itripUser);
                itripUserService.itripCreateUserByEmail(itripUser);
                return  DtoUtil.returnSuccess();
            }else {
                return DtoUtil.returnFail("用户存在，注册失败",ErrorCode.AUTH_USER_ALREADY_EXISTS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * 手机激活
     * @return
     */
    @RequestMapping(value = "/validatephone",method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public Dto validatephone(@RequestParam("user") String userCode,
                             @RequestParam("code") String code){
        try {
            if (itripUserService.validatePhone(userCode,code)) {
                return DtoUtil.returnSuccess("验证成功");
            }else {
                return DtoUtil.returnSuccess("验证失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * 邮箱激活
     * @return
     */
    @RequestMapping(value = "/activate",method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public Dto activate(@RequestParam("user") String userCode,
                             @RequestParam("code") String code){
        try {
            if (itripUserService.validateEmail(userCode,code)) {
                return DtoUtil.returnSuccess("验证成功");
            }else {
                return DtoUtil.returnSuccess("验证失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
        }
    }
}
