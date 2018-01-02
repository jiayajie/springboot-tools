package com.example.baseproject.repostitory;

import com.example.baseproject.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dongyaofeng on 2017/12/25.
 */
public interface UserRepostitory extends JpaRepository<UserModel, Integer>, /*用户多条件查询*/ JpaSpecificationExecutor<UserModel> {

    // 通过ID查询
    UserModel findById(Integer id);

    // 用户名查询
    List<UserModel> findByUsername(String username);

    //修改
    @Transactional
    @Modifying
    @Query(value = "update UserModel  u set  u.age=?2 where u.id =?1")
    void updateAge(Integer id, Integer age);

}
