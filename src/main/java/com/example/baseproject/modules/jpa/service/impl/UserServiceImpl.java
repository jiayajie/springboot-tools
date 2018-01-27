package com.example.baseproject.modules.jpa.service.impl;

import com.example.baseproject.modules.jpa.entity.UserModel;
import com.example.baseproject.modules.jpa.repository.UserRepostitory;
import com.example.baseproject.modules.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created With IDEA.
 *
 * @author dongyaofeng
 * @date 2018/1/2 15:16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepostitory userRepostitory;


    @Override
    @Transactional
    public UserModel addUser(UserModel userModel) {

        UserModel user = userRepostitory.save(userModel);

        return user;
    }

    /**
     * 多条件查询
     *
     * @param param1 条件1
     * @param param2 条件2
     * @param param3 条件3
     * @return
     */
    @Override
    public List<UserModel> queryByParams(String param1, String param2, String param3) {

        Specification<UserModel> specification = new Specification<UserModel>() {
            @Override
            public Predicate toPredicate(Root<UserModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                //添加查询条件
                List<Predicate> predicates = new ArrayList<>();

                // equal
                predicates.add(criteriaBuilder.equal(root.get("字段1"), param1));

                // between
                predicates.add(criteriaBuilder.between(root.get("字段2"), " 00:00:00", " 23:59:59"));

                // like
                predicates.add(criteriaBuilder.like(root.get("字段3"), "%" + param3 + "%"));

                // or
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("字段4"), "%" + param3 + "%"),
                        criteriaBuilder.like(root.get("字段4"), "%" + param3 + "%"),
                        criteriaBuilder.like(root.get("presenter"), "%" + param3 + "%")
                ));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        //排序
        Sort sort = new Sort(Sort.Direction.DESC, "排序字段");

        //分页
        Pageable pageable = new PageRequest(0, 10, sort);

        List<UserModel> content = userRepostitory.findAll(specification, pageable).getContent();
        return content;
    }
}
