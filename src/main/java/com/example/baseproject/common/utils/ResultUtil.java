package com.example.baseproject.common.utils;

import com.example.baseproject.common.model.ResultEntity;

/**
 * Created by Administrator on 2017/12/25.
 * MVC 返回结果工具类
 */
public class ResultUtil {

    /**
     * 请求成功2
     */
    public static ResultEntity success(Object object) {
        ResultEntity ResultEntity = new ResultEntity();
        ResultEntity.setCode(200);
        ResultEntity.setMsg("success");
        ResultEntity.setData(object);
        return ResultEntity;
    }

    /**
     * 请求成功1
     */
    public static ResultEntity success() {
        return success(null);
    }

    /**
     * 请求失败
     */
    public static ResultEntity error(Integer code, String msg) {
        return new ResultEntity(code, msg);
    }

    /**
     * PUT 操作 (返回更新的字段)
     */
    public static ResultEntity put(Object obj) {
        return new ResultEntity(201, "success", obj);
    }

    /**
     * DEL 操作
     */
    public static ResultEntity del() {
        return new ResultEntity(204, "success");
    }

    /**
     * POST 操作 (返回添加的字段)
     */
    public static ResultEntity post(Object obj) {
        return new ResultEntity(201, "success", obj);
    }
}
