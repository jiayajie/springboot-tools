package com.example.baseproject.modules.jpa.controller;

import com.example.baseproject.common.model.ResultEntity;
import com.example.baseproject.modules.jpa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单接口
 *
 * @author dongyaofeng
 * @date 2018/2/7 23:26
 */
@RestController
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("pay")
    public ResultEntity pay(Long orderNo) {
        ResultEntity pay = orderService.pay(1L, 2, "/");
        return pay;
    }
}
