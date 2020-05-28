package com.kgc.service;

import com.kgc.beans.model.ItripAreaDic;
import com.kgc.beans.model.ItripLabelDic;
import com.kgc.beans.vo.ItripLabelDicVo;

import java.util.List;

public interface ItripLabelDicService {
    /**
     * 获取酒店特色列表
     * @return
     */
    public List<ItripLabelDicVo> getHotelFeatureList()throws Exception;

    /**
     * 获取酒店床型列表
     * @return
     */
    public List<ItripLabelDicVo> getHotelRoomBedList()throws Exception;

    /**
     * 获取出游类型列表
     * @return
     */
    public List<ItripLabelDicVo> getTravelTypeList()throws Exception;



}
