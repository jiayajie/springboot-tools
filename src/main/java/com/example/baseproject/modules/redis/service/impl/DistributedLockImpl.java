package com.example.baseproject.modules.redis.service.impl;

import com.example.baseproject.modules.redis.service.DistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCommands;

/**
 * Created With IDEA.
 * redis 分布式锁
 *
 * @author dongyaofeng
 * @date 2018/2/6 12:54
 * <p>
 * <p>
 * 分布式锁一般有三种实现方式
 * 1. 数据库乐观锁；
 * 2. 基于Redis的分布式锁；
 * 3. 基于ZooKeeper的分布式锁。
 * <p>
 * 为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下四个条件：
 * <p>
 * <p>
 * 1.互斥性。在任意时刻，只有一个客户端能持有锁。
 * 2.不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
 * 3.具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
 * 4.解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
 * <p>
 * <p>
 * 正确姿势
 */
@Service

@EnableAsync
public class DistributedLockImpl implements DistributedLock {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String redis_lock = "REDIS_LOCK";

    /**
     * 获取锁
     */
    @Override
    public boolean acquire() {

//        这两行需要有 原子性 ,用下面代码替换
//        stringRedisTemplate.opsForValue().setIfAbsent("10001", "传的是requestId");
//        stringRedisTemplate.expire("10001", 5000, TimeUnit.MILLISECONDS);


        Object execute = stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) redisConnection.getNativeConnection();

                /*
                 * 第一个为key，我们使用key来当锁，因为key是唯一的。
                 * 第二个为value，传的是requestId 主要是在解锁的时候就可以有依据
                 * 第三个为NX 意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
                 * 第四个为expx，这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
                 * 第五个为time，与第四个参数相呼应，代表key的过期时间。
                 */
                return commands.set(redis_lock, "传的是requestId", "NX", "PX", 1000 * 10);
            }
        });

        String andSet = stringRedisTemplate.opsForValue().getAndSet(redis_lock, "10002");

        System.out.println(andSet);


        return false;
    }

    /**
     * 释放锁
     */
    @Override
    public void release() {

    }
}
