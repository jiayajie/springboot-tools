package com.example.baseproject.modules.mongo.controller;

import com.example.baseproject.modules.mongo.dao.UserRepository;
import com.example.baseproject.modules.mongo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/3/5 17:15
 * @Version 1.0
 */
@RestController
public class UserMongoController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    /**
     * save use before findName
     * @return
     */
    @GetMapping("/save")
    public User save() {
        User user = new User(2, "Tseng", 21);
        mongoTemplate.save(user);
        return user;
    }

    @GetMapping("/find")
    public List<User> find() {
        List<User> userList = mongoTemplate.findAll(User.class);
        return userList;
    }

    /**
     * input String name "Tseng"
     * @param name
     * @return
     */
    @GetMapping("/findByName")
    public User findByName(@RequestParam("name") String name) {
        User user = userRepository.findByName(name);
        return user;
    }
}
