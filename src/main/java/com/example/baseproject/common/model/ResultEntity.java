package com.example.baseproject.common.model;

import com.example.baseproject.common.enums.ResultEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/25.
 * MVC 返回数据据实体封装
 */
@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果是null的对象,key也会消失
public class ResultEntity<T> implements Serializable {

    /**
     * 错误码.
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 具体的内容.
     */
    private T data;

    public ResultEntity() {
    }

    public ResultEntity(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultEntity(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultEntity(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == ResultEnum.OK.getCode();
    }
}
