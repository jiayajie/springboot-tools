package com.example.baseproject.modules.sso.service.impl;

import com.example.baseproject.common.model.ResultEntity;
import com.example.baseproject.common.utils.ResultUtil;
import com.example.baseproject.modules.sso.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import java.util.concurrent.TimeUnit;

/**
 * 单点登录 token 类
 *
 * @author dongyaofeng
 * @date 2018/2/10 21:58
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获得用户 信息
     * @param token
     * @return
     */
    public ResultEntity getUserByToken(String token) {
        String json = (String) redisTemplate.opsForValue().get(token);
        if (StringUtils.isBlank(json)) {
            return ResultUtil.error(400, "Session 已经过期,请重新登录");
        }

        //更新过期时间
        redisTemplate.expire("token", 30, TimeUnit.MINUTES);

        return ResultUtil.success(json);


    }
}
