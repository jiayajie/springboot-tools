package com.example.baseproject.advice.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.elasticsearch.annotations.Document;

import java.lang.annotation.*;

/**
 * Created With IDEA.
 * 控制器访问次数限制
 *
 * @author dongyaofeng
 * @date 2018/1/30 13:15
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//最高优先级
@Order(Ordered.HIGHEST_PRECEDENCE)
@Documented
public @interface RequestLimit {
    /**
     *
     * 允许访问的次数，默认值MAX_VALUE
     */
    int count() default Integer.MAX_VALUE;

    /**
     *
     * 时间段，单位为毫秒，默认值一分钟
     */
    long time() default 60000;
}
