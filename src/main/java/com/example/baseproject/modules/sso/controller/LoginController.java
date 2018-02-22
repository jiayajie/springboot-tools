package com.example.baseproject.modules.sso.controller;

import com.example.baseproject.common.model.ResultEntity;
import com.example.baseproject.common.utils.CookieUtil;
import com.example.baseproject.common.utils.ResultUtil;
import com.example.baseproject.modules.jpa.entity.UserModel;
import com.example.baseproject.modules.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 通过 token 获取用户信息
     */
    @GetMapping("/login")
    public ResultEntity signIn(@CookieValue("SESSIONID") String token, HttpServletResponse response, String username) {

        //假如登录成功....
        UserModel userModel = new UserModel();
        userModel.setUsername("dongyaofeng");
        userModel.setAge(21);

        //写回cookie 到 浏览器
        CookieUtil.writeLoginToken(response, token);

        //存储 sesion信息 到 redis
        redisTemplate.opsForValue().set("token","" );

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
