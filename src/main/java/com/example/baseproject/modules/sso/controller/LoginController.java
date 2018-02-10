package com.example.baseproject.modules.sso.controller;

import com.example.baseproject.common.model.ResultEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 单点登录系统
 * @author dongyaofeng
 * @date 2018/2/10 21:39
 */
public class LoginController {

    /**
     * 通过 token 获取用户信息
     */

    @GetMapping("/")
    public ResultEntity getUserBYToken() {

        return null;
    }
}
