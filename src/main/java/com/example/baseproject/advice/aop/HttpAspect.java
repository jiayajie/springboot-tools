package com.example.baseproject.advice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created With IDEA.
 * AOP 缓存请求数据
 *
 * @author dongyaofeng
 * @date 2017/12/25 13:21
 */
@Aspect
@Component
public class HttpAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

//    @Pointcut("execution(public * com.example.baseproject.modules.elasticsearch.controller.LogsController.*(..))")
    @Pointcut("execution(public * com.example.baseproject.controller..*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Map<String, String> httpMap = new HashMap();
        //url
//        logger.info("url={}", request.getRequestURL());
        httpMap.put("url", String.valueOf(request.getRequestURL()));

        //method
//        logger.info("method={}", request.getMethod());
        httpMap.put("method", request.getMethod());

        //ip
//        logger.info("ip={}", request.getRemoteAddr());
        httpMap.put("ip", request.getRemoteAddr());

        //类方法
//        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        httpMap.put("method", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        //参数
//        logger.info("args={}", joinPoint.getArgs());
        httpMap.put("args", Arrays.toString(joinPoint.getArgs()));

        //缓存请求数据
        stringRedisTemplate.opsForHash().putAll("HTTP:" + "IP[" + request.getRemoteAddr() + "]" + "TIME[" + Instant.now() + "]", httpMap);

    }

//    @After("log()")
    public void doAfter() {
        logger.info("doAfter");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        logger.info("response={}", String.valueOf(object));
    }

//    @Around("log()")
    public void doAround(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Around={}", "进入环绕通知");
        pjp.proceed();//执行该方法
        logger.info("Around={}", "退出方法");
    }

}
