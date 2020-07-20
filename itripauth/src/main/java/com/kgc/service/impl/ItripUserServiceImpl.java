package com.kgc.service.impl;
import com.kgc.beans.vo.ItripUserVo;
import com.kgc.exception.UserLoginFailedException;
import com.kgc.service.ItripUserService;
import com.kgc.dao.ItripUserMapper;
import com.kgc.beans.model.ItripUser;
import com.kgc.service.MailService;
import com.kgc.service.SmsService;
import com.kgc.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * ItripUserServiceImpl
 * 李文俊
 * 2020.7.20
 */
@Service("itripUserService")
public class ItripUserServiceImpl implements ItripUserService {
    @Resource
    private ItripUserMapper itripUserMapper;

    @Autowired
    private SmsService smsService;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisAPI redisAPI;

    private static Logger logger = Logger.getLogger(ItripUserServiceImpl.class);

    private int expire = 1;//过期时间（分钟）

    public ItripUser getById(Long id)throws Exception{
        return itripUserMapper.getById(id);
    }

    @Override
    public ItripUser login(String userCode,String password) throws Exception {
        ItripUser user = this.findByUserCode(userCode);
        if (EmptyUtils.isNotEmpty(user) && user.getUserPassword().equals(password)) {
            if (user.getActivated() != 1) {
                throw new UserLoginFailedException("用户未激活");
            }
            return user;
        }else
            return null;
    }

    @Override
    public ItripUser findByUserCode(String userCode) throws Exception {
        Map<String ,Object> param = new HashMap<>();
        param.put("userCode",userCode);
        List<ItripUser> listByName = itripUserMapper.getListByMap(param);
        if (listByName.size() > 0){
            return listByName.get(0);
        }else
        return null;
    }

    @Override
    public void itripCreateUserByPhone(ItripUser itripUser) throws Exception {
        //发送验证码
        String code = String.valueOf(MD5.getRandomCode());
        smsService.send(itripUser.getUserCode(),"1",new String[]{code,String.valueOf(expire)} );
        //缓存验证码
        String key = "activation"+itripUser.getUserCode();
        redisAPI.set(key,expire*60,code);
        //保存用户信息
        itripUserMapper.save(itripUser);
    }

    @Override
    public void itripCreateUserByEmail(ItripUser itripUser) throws Exception {
        //发送验证码
        String code = String.valueOf(MD5.getRandomCode());
        mailService.sendActivationMail(itripUser.getUserCode(),Integer.valueOf(code));
        //缓存验证码
        String key = "activation"+itripUser.getUserCode();
        redisAPI.set(key,expire*60,code);
        //保存用户信息
        itripUserMapper.save(itripUser);
    }

    @Override
    public boolean validatePhone(String phone, String code)throws Exception {
        String key = "activation"+phone;
        if (redisAPI.exist(key)){
            if (redisAPI.get(key).equals(code)){
                ItripUser itripUser = this.findByUserCode(phone);
                if (EmptyUtils.isNotEmpty(itripUser)){
                    logger.debug("激活用户"+phone);
                    itripUser.setActivated(1);
                    itripUser.setFlatID(itripUser.getId());
                    itripUserMapper.modify(itripUser);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean validateEmail(String email, String code) throws Exception {
        String key = "activation"+email;
        if (redisAPI.exist(key)){
            if (redisAPI.get(key).equals(code)){
                ItripUser itripUser = this.findByUserCode(email);
                if (EmptyUtils.isNotEmpty(itripUser)){
                    logger.debug("激活用户"+email);
                    itripUser.setActivated(1);
                    itripUser.setFlatID(itripUser.getId());
                    itripUserMapper.modify(itripUser);
                    return true;
                }
            }
        }
        return false;
    }

    public List<ItripUser> getListByMap(Map<String,Object> param)throws Exception{
        return itripUserMapper.getListByMap(param);
    }

    public Integer getCountByMap(Map<String,Object> param)throws Exception{
        return itripUserMapper.getCountByMap(param);
    }

    public Integer save(ItripUser itripUser)throws Exception{
            itripUser.setCreationDate(new Date());
            return itripUserMapper.save(itripUser);
    }

    public Integer modify(ItripUser itripUser)throws Exception{
        itripUser.setModifyDate(new Date());
        return itripUserMapper.modify(itripUser);
    }

    public Integer removeById(Long id)throws Exception{
        return itripUserMapper.removeById(id);
    }

    public Page<List<ItripUser>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripUserMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripUser> itripUserList = itripUserMapper.getListByMap(param);
        page.setRows(itripUserList);
        return page;
    }

}
