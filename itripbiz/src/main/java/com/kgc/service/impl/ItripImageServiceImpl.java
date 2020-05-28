package com.kgc.service.impl;

import com.kgc.beans.model.ItripImage;
import com.kgc.dao.ItripImageMapper;
import com.kgc.service.ItripImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("itripImageService")
public class ItripImageServiceImpl implements ItripImageService {
    @Resource
    private ItripImageMapper itripImageMapper;
    @Override
    public List<ItripImage> getHotelImgById(Long id) throws Exception {
        List<ItripImage> imgById = itripImageMapper.getImgById(id,"0");
        return imgById;
    }

    @Override
    public List<ItripImage> getHotelroomImgById(Long id) throws Exception {
        List<ItripImage> imgById = itripImageMapper.getImgById(id,"1");
        return imgById;
    }

    @Override
    public List<ItripImage> getTalkImgById(Long id) throws Exception {
        List<ItripImage> imgById = itripImageMapper.getImgById(id,"2");
        return imgById;
    }

    @Override
    public boolean save(ItripImage itripImage) throws Exception {
        itripImage.setCreationDate(new Date());
        if (itripImageMapper.save(itripImage)>0){
            return true;
        }
        return false;
    }
}
