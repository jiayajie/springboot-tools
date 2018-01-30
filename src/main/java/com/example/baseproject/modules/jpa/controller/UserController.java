package com.example.baseproject.modules.jpa.controller;

import com.example.baseproject.advice.annotation.RequestLimit;
import com.example.baseproject.common.model.ResultEntity;
import com.example.baseproject.modules.jpa.entity.UserModel;
import com.example.baseproject.modules.jpa.repository.UserRepostitory;
import com.example.baseproject.modules.jpa.service.UserService;
import com.example.baseproject.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * spring data jpa learn
 * rest controller
 * get post put delete
 *
 * @author dongyaofeng
 * @date 2018/1/2 15:37
 */
@RestController
public class UserController {

    @Autowired
    /*为了方便直接注入 UserRepostitory*/
    private UserRepostitory userRepostitory;

    @Autowired
    private UserService userService;

    /**
     * 添加用户
     *
     * @param userModel
     * @return user
     */
    @RequestLimit
    @PostMapping("users")
    public ResultEntity<UserModel> addUser(UserModel userModel) {
        UserModel user = userService.addUser(userModel);
        return ResultUtil.post(user);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return code 204
     */
    @DeleteMapping("users/{id}")
    public ResultEntity<UserModel> delUser(@PathVariable(name = "id") Integer id) {
        userRepostitory.delete(id);
        return ResultUtil.del();
    }


    /**
     * 修改 用户部分信息
     *
     * @param id
     * @param userModel
     * @return 修改字段的 json串
     */
    @PutMapping("users/{id}")
    public ResultEntity<UserModel> updateUser(@PathVariable(name = "id", required = true) Integer id, UserModel userModel) {
        userModel.setId(id);
        UserModel user = userRepostitory.save(userModel);
        return ResultUtil.put(user);
    }


    /**
     * 根据id 查询user
     *
     * @param id 用户id
     * @return user
     */
    @GetMapping("users/{id}")
    @RequestLimit(count=10,time=10)
    public ResultEntity<UserModel> findUserById(@PathVariable(name = "id", required = false) Integer id) {

        UserModel one = userRepostitory.findById(id);
        return ResultUtil.success(one);
    }

    /**
     * 查询所有
     *
     * @return List<UserModel>
     */
    @GetMapping("/users")
    public ResultEntity findAll() {
        List<UserModel> all = userRepostitory.findAll();
        return ResultUtil.success(all);
    }
}
