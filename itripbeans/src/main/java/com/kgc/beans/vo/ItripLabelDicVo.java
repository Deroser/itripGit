package com.kgc.beans.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * ItripLabelDic表
 * 李文俊
 * 2020.7.20
 */
public class ItripLabelDicVo implements Serializable {

    //主键
    private Long id;
    //key值
    private String name;
    //描述
    private String description;
    //标签图片地址
    private String pic;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }


    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return this.pic;
    }

}
