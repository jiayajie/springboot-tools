package com.example.baseproject;

import com.example.baseproject.modules.jpa.entity.Order;
import com.example.baseproject.modules.jpa.entity.UserModel;
import com.example.baseproject.modules.jpa.repository.OrderRepostitory;
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
public class OrderCRUDTest {

    @Autowired
    private OrderRepostitory orderRepostitory;

    @Test
    public void test() {
        Order order = orderRepostitory.findByUserIdAndOrderNo(1, 2L);
        System.out.println(order.toString());
    }


}
