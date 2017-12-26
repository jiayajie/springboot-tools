package com.example.baseproject.service.impl;

import com.example.baseproject.service.RedisTestService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created With IDEA.
 *
 * @author dongyaofeng
 * @date 2017/12/25 17:35
 */
@Service
public class RedisTestServiceImpl implements RedisTestService {

    @Override
    @Cacheable(value = "test")
    public String test(String name) {
        return "  helloworld !!! ";
    }
}
