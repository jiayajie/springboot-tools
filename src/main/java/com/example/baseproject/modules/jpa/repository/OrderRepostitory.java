package com.example.baseproject.modules.jpa.repository;

import com.example.baseproject.modules.jpa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/25 16:05
 * @Version 1.0
 */
public interface OrderRepostitory extends JpaRepository<Order, Long> {

    Order findByUserIdAndOrderNo(Integer userId, Long orderNo);
}
