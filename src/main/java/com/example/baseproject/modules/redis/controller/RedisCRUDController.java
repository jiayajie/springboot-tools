package com.example.baseproject.modules.redis.controller;

import com.example.baseproject.common.model.ResultEntity;
import com.example.baseproject.common.utils.ResultUtil;
import com.example.baseproject.modules.jpa.entity.UserModel;
import com.example.baseproject.modules.jpa.repository.UserRepostitory;
import com.example.baseproject.modules.redis.service.RedisAnnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    private RedisAnnoService redisAnnoService;

    @Autowired
    private UserRepostitory userRepostitory;

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


    /** #################################### 以下是通过注解的方式记录 缓存  ######################################## */


    /**
     * 测试 参数 为 mapList  结果为 mapList  接口
     *
     * @param id 前台 参数
     * @return mapList
     */
    @GetMapping("/anno/{id}")
    public ResultEntity redisCache(@PathVariable String id) {
        Map<String, List<String>> mapList = new LinkedHashMap<>();

        List<String> list = new ArrayList<>();
        list.add(id);
        mapList.put("这是来自前台URL上的参数", list);

        //获取线程名称
        String threadName = Thread.currentThread().getName();

        //第一次走后台 , 第二次走缓存
        Map<String, List<String>> stringListMap = redisAnnoService.addCacheByAnno(mapList);

        //再次获取线程名称
        String threadName_back = Thread.currentThread().getName();

        String msg = "The Data from Mysql";
        if (threadName.equals(threadName_back)) {
            msg = "The Data from Redis";
        }
        return new ResultEntity(200, msg, stringListMap);
    }


    /**
     * 测试 参数 为 Object  结果为 Object  接口
     */
    @GetMapping("/anno/users/{id}")
    public ResultEntity redisCache4Obj(@PathVariable Integer id) {

        //获取线程名称
        String threadName = Thread.currentThread().getName();

        //获得User 数据 作为Key
        UserModel userModel = userRepostitory.findById(id);

        //第一次走后台 , 第二次走缓存
        UserModel obj = redisAnnoService.addCacheByAnno(userModel);

        //再次获取线程名称
        String threadName_back = Thread.currentThread().getName();

        String msg = "The Data from Mysql";
        if (threadName.equals(threadName_back)) {
            msg = "The Data from Redis";
        }
        return new ResultEntity(200, msg, obj);
    }
}
