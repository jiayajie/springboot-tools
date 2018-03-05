package com.example.baseproject;

import com.example.baseproject.modules.jpa.entity.UserModel;
import com.example.baseproject.modules.jpa.repository.UserRepostitory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created With IDEA.
 *
 * @author dongyaofeng
 * @date 2018/1/2 14:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserCRUDTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserRepostitory userRepostitory;

    /**
     * 保存用户信息
     */
    @Test
    public void saveUser() {
        List list = new ArrayList();
        for (int i = 0; i < 10000; i++) {
            UserModel userModel = new UserModel();
            userModel.setAge(Long.hashCode(Math.round(Math.random()*100)));
            userModel.setPassworld("123456");
            userModel.setUsername("dongyaofeng"+i);
            userModel.setHobby("踢足球"+i);
            userModel.setBirthday("06-12"+i);
            list.add(userModel);
        }
        userRepostitory.save(list);
    }

    @Test
    public void getUsers() {
//        UserModel one = userRepostitory.findById(1);
//        UserModel one = userRepostitory.getOne(1);
//        UserModel byId = userRepostitory.findById(2);
//        List<UserModel> dong = userRepostitory.findByUsername("dongyaofeng");
        userRepostitory.updateAge(2,99);
    }

    @Test
    public void serRedis() {
        stringRedisTemplate.opsForValue().set("miaomiao", "02505520");
    }
}
