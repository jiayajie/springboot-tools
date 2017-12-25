package com.example.baseproject.utils;

import com.example.baseproject.domain.ResultEntity;

/**
 * Created by Administrator on 2017/12/25.
 *  MVC 返回结果工具类
 */
public class ResultUtil {
    public static ResultEntity success(Object object) {
        ResultEntity ResultEntity = new ResultEntity();
        ResultEntity.setCode(0);
        ResultEntity.setMsg("success");
        ResultEntity.setData(object);
        return ResultEntity;
    }

    public static ResultEntity success() {
        return success(null);
    }

    public static ResultEntity error(Integer code, String msg) {
        ResultEntity ResultEntity = new ResultEntity();
        ResultEntity.setCode(code);
        ResultEntity.setMsg(msg);
        return ResultEntity;
    }
}
