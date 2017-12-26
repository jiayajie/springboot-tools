package com.example.baseproject.controller;

import com.example.baseproject.domain.ResultEntity;
import com.example.baseproject.service.RedisTestService;
import com.example.baseproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created With IDEA.
 *
 * @author dongyaofeng
 * @date 2017/12/26 15:54
 */
@RestController
public class TestController {
    @Autowired
    RedisTestService redisTestService;

    @GetMapping("/redis")
    public ResultEntity testRedis() {
        redisTestService.test("test");
        return ResultUtil.success();
    }
}
