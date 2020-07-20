package com.kgc.dao;

import java.io.Serializable;
/**
 * Param
 * 李文俊
 * 2020.7.20
 */
//单个
public class Param implements Serializable {
    private String key;
    private Object value;
    private String operator;//分隔符

    public Param(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Param(String key, Object value, String operator) {
        this.key = key;
        this.value = value;
        this.operator = operator;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
