package com.example.baseproject.advice.aop;

import com.example.baseproject.advice.annotation.RequestLimit;
import com.example.baseproject.advice.exception.custom.RequestLimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created With IDEA.
 * 注解的具体实现类
 *
 * @author dongyaofeng
 * @date 2018/1/30 13:30
 */
@Aspect
@Component
public class RequestLimitAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户访问次数限制
     *
     * @param params 访问次数
     * @throws RequestLimitException
     */
    @Before("execution(public * com.example.baseproject.modules..controller..*(..)) && @annotation(params)")
    public void requestLimit(JoinPoint joinPoint, RequestLimit params) throws RequestLimitException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String localAddr = request.getLocalAddr();

        //限制次数
        int count = params.count();

        String redisKey = "HTTP-Intercept:" + localAddr;

        if (stringRedisTemplate.hasKey(redisKey)) {
            stringRedisTemplate.boundValueOps(redisKey).increment(1);

            //限制
            if (count < Integer.valueOf(stringRedisTemplate.opsForValue().get(redisKey))) {
                //重新刷新时间
                stringRedisTemplate.expire(redisKey, params.time(), TimeUnit.SECONDS);
                //如果拦截次数 超过限制, 将加入黑名单 不让访问了

                throw new RequestLimitException(localAddr);
            }
        } else {
            stringRedisTemplate.opsForValue().set(redisKey, "0", params.time(), TimeUnit.SECONDS);
        }
    }
}
