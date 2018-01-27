package com.example.baseproject;

import com.example.baseproject.modules.jpa.entity.UserModel;
import com.example.baseproject.modules.jpa.repository.UserRepostitory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    private UserRepostitory userRepostitory;

    /**
     * 保存用户信息
     */
    @Test
    public void saveUser() {
        UserModel userModel = new UserModel();
        userModel.setAge(22);
        userModel.setUsername("dongyaofeng");
        userModel.setHobby("踢足球");
        userModel.setBirthday("06-12");
        userRepostitory.save(userModel);
    }

    @Test
    public void getUsers() {
//        UserModel one = userRepostitory.findById(1);
//        UserModel one = userRepostitory.getOne(1);
//        UserModel byId = userRepostitory.findById(2);
//        List<UserModel> dong = userRepostitory.findByUsername("dongyaofeng");
        userRepostitory.updateAge(2,99);
    }
}
