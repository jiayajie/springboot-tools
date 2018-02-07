package com.example.baseproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created With IDEA.
 *
 * @author dongyaofeng
 * @date 2018/1/30 15:47
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class HttpTest {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void test() {
        String url = "http://192.168.102.36:8080/users/1";
        //参数
        Object body = restTemplate.getForEntity(url, Object.class).getBody();
        System.out.println(body);
    }

}
