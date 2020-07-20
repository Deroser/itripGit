package com.kgc.service.impl;

import com.kgc.beans.model.ItripAreaDic;
import com.kgc.beans.vo.BizCityIdAndNameVo;
import com.kgc.dao.ItripAreaDicMapper;
import com.kgc.service.ItripAreaDicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * ItripAreaDicServiceImpl
 * 李文俊
 * 2020.7.20
 */
@Service("itripAreaDicService")
public class ItripAreaDicServiceImpl implements ItripAreaDicService {
    @Resource
    private ItripAreaDicMapper itripAreaDicMapper;

    /**
     * 查询国内外热门城市
     */
    @Override
    public List<BizCityIdAndNameVo> getHotCityListByIsChina(String isChina) throws Exception {
        Map<String ,Object> param = new HashMap<>();
        param.put("isChina",isChina);
        param.put("isHot",1);
        List<BizCityIdAndNameVo> listByMap = itripAreaDicMapper.getIdAndNameListByMap(param);
        return listByMap;
    }

    @Override
    public List<BizCityIdAndNameVo> getAreaByIsChina(String cityId) throws Exception {
        Map<String ,Object> param = new HashMap<>();
        param.put("parent",cityId);
        param.put("isTradingArea",1);
        List<BizCityIdAndNameVo> listByMap = itripAreaDicMapper.getIdAndNameListByMap(param);
        return listByMap;
    }
}
