package com.kgc.service.impl;

import com.kgc.beans.model.ItripAreaDic;
import com.kgc.beans.model.ItripLabelDic;
import com.kgc.beans.vo.ItripLabelDicVo;
import com.kgc.dao.ItripAreaDicMapper;
import com.kgc.dao.ItripLabelDicMapper;
import com.kgc.service.ItripLabelDicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("itripLabelDicService")
public class ItripLabelDicServiceImpl implements ItripLabelDicService {
    @Resource
    private ItripLabelDicMapper itripLabelDicMapper;



    /**
     * 获取酒店特色列表
     * @return
     */
    @Override
    public List<ItripLabelDicVo> getHotelFeatureList() throws Exception{
        Map<String ,Object > param = new HashMap<>();
        param.put("parentId",16);
        List<ItripLabelDicVo> listByMap = itripLabelDicMapper.getLabelDicVoListByMap(param);
        return listByMap;
    }

    @Override
    public List<ItripLabelDicVo> getHotelRoomBedList() throws Exception {
        Map<String ,Object > param = new HashMap<>();
        param.put("parentId",1);
        List<ItripLabelDicVo> listByMap = itripLabelDicMapper.getLabelDicVoListByMap(param);
        return listByMap;
    }

    @Override
    public List<ItripLabelDicVo> getTravelTypeList() throws Exception {
        Map<String ,Object > param = new HashMap<>();
        param.put("parentId",107);
        List<ItripLabelDicVo> listByMap = itripLabelDicMapper.getLabelDicVoListByMap(param);
        return listByMap;
    }


}
