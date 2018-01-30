package com.example.baseproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/30 11:42
 * @Version 1.0
 */
@Configuration
//maxInactiveIntervalInSeconds session超时时间,单位秒
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600)
public class RedisSessionConfig {
}
