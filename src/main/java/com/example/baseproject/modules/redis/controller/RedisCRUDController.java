package com.example.baseproject.modules.redis.controller;

import com.example.baseproject.common.model.ResultEntity;
import com.example.baseproject.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis CRUD 操作展示类
 *
 * @author dongyaofeng
 * @date 2018/2/7 0:14
 */
@RestController
@RequestMapping("/redis")
public class RedisCRUDController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取所有key - value
     *
     * @return
     */
    @GetMapping("")
    public ResultEntity getAllKeys(HttpServletRequest request) {
        Map<String, String> keyValue = new HashMap<>();
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            String value = stringRedisTemplate.opsForValue().get(key);
            keyValue.put(key, value);
        }
        return ResultUtil.success(keyValue);
    }

    /**
     * 设置 key 和 value
     *
     * @param key   key
     * @param value value
     * @param time  设置过期时间 默认 无
     * @return
     */
    @GetMapping(value = {"/{key}/{value}", "{key}/{value}/{time}"})
    public ResultEntity saveKeyAndValue(@PathVariable String key, @PathVariable String value, @PathVariable(required = false) Long time) {
        if (time != null) {
            stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(key, value);
        }

        return ResultUtil.success();
    }

    /**
     * 通过 key 获取 value
     */
    @GetMapping("/{key}")
    public ResultEntity getVaueByKey(@PathVariable String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return ResultUtil.success(value);
    }

    /**
     * 通过 key 删除 value
     */
    @DeleteMapping("/{key}")
    public ResultEntity deleteVaueByKey(@PathVariable String key) {
        if (stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.delete(key);
        }
        return ResultUtil.del();
    }

    /**
     * 删除 所有
     */
    @DeleteMapping("/all")
    public ResultEntity deleteAll() {
        Set<String> keys = stringRedisTemplate.keys("*");
        stringRedisTemplate.delete(keys);
        return ResultUtil.del();
    }


}
