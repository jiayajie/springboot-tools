package com.example.baseproject.modules.jpa.service;

import com.example.baseproject.common.model.ResultEntity;

/**
 * @author dongyaofeng
 * @date 2018/2/7 23:30
 */
public interface OrderService {

    ResultEntity pay(Long orderNo, Integer userId, String path);
}
