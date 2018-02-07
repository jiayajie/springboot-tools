package com.example.baseproject.modules.redis.service.impl;

import com.example.baseproject.modules.jpa.entity.UserModel;
import com.example.baseproject.modules.redis.service.RedisAnnoService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created With IDEA.
 * 通过注解的方式记录 缓存
 *
 * @author dongyaofeng
 * @date 2018/2/7 12:56
 */
@Service
public class RedisAnnoServiceImpl implements RedisAnnoService {


    /**
     * 添加缓存  无参 类型
     *
     * @return str
     */
    @Override
    @Cacheable(/*cache名称*/value = "nop", /*key = "",缓存key */ condition = "true" /*是否缓存表达式,支持SprinfEL*/)
    public String addCacheByAnno() {
        return "This is a value from  no prarms";
    }

    /**
     * 添加缓存  str 类型
     *
     * @param str
     * @return str
     */
    @Override
    @Cacheable(value = "str")
    public String addCacheByAnno(String str) {
        return "This is a value from  prarm = " + str;
    }

    /**
     * 添加缓存  str 类型
     *
     * @param str
     * @param str2
     * @return str[]
     */
    @Override
    @Cacheable(value = "str[]")
    public String[] addCacheByAnno(String str, String str2) {
        List<String> list = new ArrayList<>();
        list.add(str);
        list.add(str2);
        list.add("这是来自后台的 数组 ");
        String[] array = list.toArray(new String[]{});
        return array;
    }

    /**
     * 添加缓存  list 类型
     *
     * @param list
     * @return list
     */
    @Override
    @Cacheable(value = "list")
    public List<String> addCacheByAnno(List<String> list) {
        list.add("这是来自后台关于 requst 的 数据");
        return list;
    }

    /**
     * 添加缓存  mapList 类型
     *
     * @param mapList
     * @return mapList
     */
    @Override
    @Cacheable(value = "mapList")
    public Map<String, List<String>> addCacheByAnno(Map<String, List<String>> mapList) {

        List<String> list = new ArrayList<>();

        Thread thread = Thread.currentThread();
        list.add("name: " + thread.getName());
        list.add("id: " + thread.getId());
        list.add("time: " + System.currentTimeMillis());
        mapList.put("这些参数来自后台的Thread", list);

        thread.setName(thread + UUID.randomUUID().toString());

        return mapList;
    }

    /**
     * 添加缓存  Object 类型
     *
     * @param obj
     * @return obj
     */
    @Override
    @Cacheable(value = "obj")
    public UserModel addCacheByAnno(Object obj) {
        UserModel userModel = (UserModel) obj;
        Thread.currentThread().setName(Thread.currentThread().getName() + UUID.randomUUID().toString());
        return userModel;
    }
}
