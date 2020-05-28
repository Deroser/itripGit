package com.kgc.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//多个
public class SolrParam implements Serializable {
    private List<Param> paramList = new ArrayList<>();

    public void add(String key,Object value){
        Param param = new Param(key,value);
        paramList.add(param);
    }

    public void add(String key,Object value,String operator){
        Param param = new Param(key,value,operator);
        paramList.add(param);
    }

    public List<Param> getParamList() {
        return paramList;
    }
}
