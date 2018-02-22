package com.example.baseproject.modules.sso.controller;

import com.example.baseproject.common.model.ResultEntity;
import com.example.baseproject.common.utils.ResultUtil;
import com.example.baseproject.modules.jpa.entity.UserModel;
import com.example.baseproject.modules.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 单点登录
 *
 * @author dongyaofeng
 * @date 2018/2/10 21:39
 * <p>
 * 原理:
 * 1.将sessionid 作为 key  用户信息 作为 value  存入 redis 中
 * 2.将serssionid 存入 cookie 中
 * 3.用户登录先从 cookie 中 获取 serssionid , 再到redis 中 获取用户信息
 */
@RestController
public class LoginController {

    @Autowired
    private TokenService tokenService;

    /**
     * 通过 token 获取用户信息
     */
    @GetMapping("/")
    public ResultEntity signIn(@CookieValue("SESSIONID") String token, String username) {

        //第一次登录
        if (token == null) {
            System.out.println("正在登录....");

//            UserModel userModel = new UserModel();

//            tokenService.saveTokenToRedis(username);
            return ResultUtil.success();
        }


        return null;
    }

    /**
     * 退出
     *
     * @return
     */
    @GetMapping("/signout")
    public ResultEntity signOut() {
        return null;
    }
}
