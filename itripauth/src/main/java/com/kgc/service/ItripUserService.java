package com.kgc.service;
import com.kgc.beans.model.ItripUser;
import java.util.List;
import java.util.Map;

import com.kgc.utils.Page;

/**
 * ItripUserService
 * 李文俊
 * 2020.7.20
 */
public interface ItripUserService {

    public ItripUser getById(Long id)throws Exception;

    /**
     * 用户登录
     * @param userCode
     * @param password
     * @return
     * @throws Exception
     */
    public ItripUser login(String userCode,String password)throws Exception;

    /**
     * 根据用户名查找用户信息
     * @param userCode
     * @return
     * @throws Exception
     */
    public ItripUser findByUserCode(String userCode)throws Exception;

    /**
     * 使用手机号创建用户账号
     * @param itripUser
     * @throws Exception
     */
    public void itripCreateUserByPhone(ItripUser itripUser)throws Exception;

    /**
     * 使用邮箱创建用户账号
     * @param itripUser
     * @throws Exception
     */
    public void itripCreateUserByEmail(ItripUser itripUser)throws Exception;

    /**
     * 通过验证码激活手机注册用户
     * @return
     * @throws Exception
     */
    public boolean validatePhone(String phone,String code)throws Exception;

    /**
     * 通过验证码激活邮箱注册用户
     * @param email
     * @param code
     * @return
     */
    public boolean validateEmail(String email,String code)throws Exception;

    public List<ItripUser>	getListByMap(Map<String, Object> param)throws Exception;

    public Integer getCountByMap(Map<String, Object> param)throws Exception;

    public Integer save(ItripUser itripUser)throws Exception;

    public Integer modify(ItripUser itripUser)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public Page<List<ItripUser>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
