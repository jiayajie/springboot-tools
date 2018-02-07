package com.example.baseproject.modules.redis.service;

import com.example.baseproject.modules.jpa.entity.UserModel;

import java.util.List;
import java.util.Map;

/**
 * Created With IDEA.
 *
 * @author dongyaofeng
 * @date 2018/2/7 12:55
 */
public interface RedisAnnoService {

    /**
     * 添加缓存  无参 类型
     *
     * @return str
     */

    String addCacheByAnno();

    /**
     * 添加缓存  str 类型
     *
     * @return str
     */
    String addCacheByAnno(String str);

    /**
     * 添加缓存  str 类型
     *
     * @param str
     * @param str2
     * @return str[]
     */
    String[] addCacheByAnno(String str, String str2);

    /**
     * 添加缓存  list 类型
     *
     * @return list
     */
    List<String> addCacheByAnno(List<String> list);

    /**
     * 添加缓存  mapList 类型
     *
     * @return mapList
     */
    Map<String, List<String>> addCacheByAnno(Map<String, List<String>> mapList);

    /**
     * 添加缓存  Object 类型
     *
     * @return obj
     */
    UserModel addCacheByAnno(Object obj);

}
