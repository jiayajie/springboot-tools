package com.example.baseproject.modules.redis.service;

/**
 * Created With IDEA.
 * redis 分布式锁
 * @author dongyaofeng
 * @date 2018/2/6 13:17
 */
public interface DistributedLock {

    /**
     * 获取锁
     */
    boolean acquire();

    /**
     * 释放锁
     */
    void release();
}
