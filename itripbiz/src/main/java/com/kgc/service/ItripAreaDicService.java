package com.kgc.service;

import com.kgc.beans.vo.BizCityIdAndNameVo;

import java.util.List;
/**
 * ItripAreaDicService
 * 李文俊
 * 2020.7.20
 */
public interface ItripAreaDicService {
    /**
     * 查询国内外热门城市
     */
    public List<BizCityIdAndNameVo> getHotCityListByIsChina(String isChina) throws Exception;

    /**
     * 查询商圈
     */
    public List<BizCityIdAndNameVo> getAreaByIsChina(String cityId) throws Exception;
}
