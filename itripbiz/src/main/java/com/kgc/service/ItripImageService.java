package com.kgc.service;

import com.kgc.beans.model.ItripImage;

import java.util.List;
/**
 * ItripImageService
 * 李文俊
 * 2020.7.20
 */
public interface ItripImageService {
    public List<ItripImage> getHotelImgById(Long id)throws Exception;
    public List<ItripImage> getHotelroomImgById(Long id)throws Exception;
    public List<ItripImage> getTalkImgById(Long id)throws Exception;
    public boolean save(ItripImage itripImage)throws Exception;
}
