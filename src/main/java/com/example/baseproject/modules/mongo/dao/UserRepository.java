package com.example.baseproject.modules.mongo.dao;

import com.example.baseproject.modules.mongo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/3/5 17:14
 * @Version 1.0
 */
public interface UserRepository extends MongoRepository<User, Integer>{
    User findByName(String name);
}
