package com.example.baseproject.service;

import com.example.baseproject.domain.UserModel;

import java.util.List;

/**
 * Created With IDEA.
 *
 * @author dongyaofeng
 * @date 2018/1/2 15:16
 */
public interface UserService {

    //多条件查询
    List<UserModel> queryByParams(String param1, String param2, String param3);
}
