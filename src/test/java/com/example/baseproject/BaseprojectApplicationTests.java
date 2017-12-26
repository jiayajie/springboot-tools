package com.example.baseproject;

import com.example.baseproject.service.RedisTestService;
import org.apache.lucene.index.ReaderSlice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseprojectApplicationTests {

	@Autowired
	private RedisTestService redisTestService;

}
