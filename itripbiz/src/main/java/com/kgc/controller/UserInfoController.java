package com.kgc.controller;

import com.kgc.beans.model.ItripUserLinkUser;
import com.kgc.beans.vo.ItripAddUserLinkUserVO;
import com.kgc.beans.vo.ItripSearchUserLinkUserVO;
import com.kgc.service.ItripUserLinkUserService;
import com.kgc.service.TokenService;
import com.kgc.utils.Dto;
import com.kgc.utils.DtoUtil;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.ErrorCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * UserInfoController
 * 李文俊
 * 2020.7.20
 */
@Controller
@RequestMapping("/api/userinfo")
public class UserInfoController {
    @Resource
    private TokenService biztokenService;
    @Resource
    private ItripUserLinkUserService itripUserLinkUserService;

    @RequestMapping(value = "/queryuserlinkuser", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto queryuserlinkuser(@RequestBody ItripSearchUserLinkUserVO itripSearchUserLinkUserVO,
                                 HttpServletRequest request) {
        //验证token
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        } else {
            List<ItripUserLinkUser> listByMap = null;
            String[] tokenDeteils = token.split("-");
            Map<String, Object> param = new HashMap<>();
            param.put("userId", tokenDeteils[2]);
            param.put("linkUserName", itripSearchUserLinkUserVO.getLinkUserName());
            try {
                listByMap = itripUserLinkUserService.getListByMap(param);
                return DtoUtil.returnSuccess("获取常用联系人信息成功", listByMap);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("获取常用联系人信息失败", "10401");
            }
        }
    }

    @RequestMapping(value = "/adduserlinkuser", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto adduserlinkuser(@RequestBody ItripUserLinkUser linkUser,
                               HttpServletRequest request) {
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        } else {
            if (EmptyUtils.isEmpty(linkUser)) {
                return DtoUtil.returnFail("不能提交空", "10412");
            }
            try {
                ItripAddUserLinkUserVO i = new ItripAddUserLinkUserVO();
                String[] tokenDeteils = token.split("-");
                linkUser.setUserId(Integer.valueOf(tokenDeteils[2]));
                linkUser.setCreatedBy(Long.valueOf(tokenDeteils[2]));
                boolean b = itripUserLinkUserService.save(linkUser);
                if (b) {
                    return DtoUtil.returnSuccess("新增常用联系人信息成功");
                } else {
                    return DtoUtil.returnFail("新增常用联系人信息失败", "10411");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.ERROR);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.ERROR);
            }
        }
    }

    @RequestMapping(value = "/modifyuserlinkuser", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto modifyuserlinkuser(@RequestBody ItripUserLinkUser linkUser,
                                  HttpServletRequest request) {
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        } else {
            if (EmptyUtils.isEmpty(linkUser)) {
                return DtoUtil.returnFail("不能提交空", "10412");
            }

            try {
                ItripAddUserLinkUserVO i = new ItripAddUserLinkUserVO();
                String[] tokenDeteils = token.split("-");
                linkUser.setModifiedBy(Long.valueOf(tokenDeteils[2]));
                boolean b = itripUserLinkUserService.modify(linkUser);
                if (b) {
                    return DtoUtil.returnSuccess("修改常用联系人信息成功");
                } else {
                    return DtoUtil.returnFail("修改常用联系人信息失败", "10411");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.ERROR);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.ERROR);
            }
        }
    }

    @RequestMapping(value = "/deluserlinkuser", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto deluserlinkuser(@RequestParam Long[] ids,
                               HttpServletRequest request) {
        String token = request.getHeader("token");
        if (!biztokenService.validates(request.getHeader("user-agent"), token)) {
            return DtoUtil.returnFail("token失效，请重新登录", "10000");
        } else {
            if (EmptyUtils.isEmpty(ids) || ids.length == 0) {
                return DtoUtil.returnFail("请选择要删除的常用联系人", "10433");
            }
            try {
                for (Long id : ids) {
                    if (!itripUserLinkUserService.whetherToPay(id)) {
                        return DtoUtil.returnFail("所选的常用联系人中有与某条待支付的订单关联的项，无法删除", "10431");
                    }
                }
                for (Long id : ids) {
                    if (!itripUserLinkUserService.removeById(id)) {
                        return DtoUtil.returnFail("删除常用联系人信息失败", "10432");
                    }
                }
                return DtoUtil.returnSuccess("删除常用联系人信息成功");
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.ERROR);
            }
        }
    }
}
