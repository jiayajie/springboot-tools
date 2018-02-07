package com.example.baseproject.modules.jpa.repository;

import com.example.baseproject.modules.jpa.entity.Order;
import com.example.baseproject.modules.jpa.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/25 16:05
 * @Version 1.0
 */
public interface OrderItemRepostitory extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderNoAndUserId(Long orderNo, Integer userId);
}
