package com.example.baseproject.controller;

import com.example.baseproject.domain.ResultEntity;
import com.example.baseproject.service.MailService;
import com.example.baseproject.service.RedisTestService;
import com.example.baseproject.utils.ResultUtil;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created With IDEA.
 *
 * @author dongyaofeng
 * @date 2017/12/26 15:54
 */
@RestController
public class TestController {


    @Autowired
    RedisTestService redisTestService;

    @Autowired
    MailService mailService;

    @GetMapping("/redis")
    public ResultEntity testRedis() {
        redisTestService.test("test");
        return ResultUtil.success();
    }


    @GetMapping("/mail")
    public ResultEntity testMail() throws Exception {
        //封装邮件信息
        Map<String, Object> model = new HashMap();
        model.put("time", new Date());
        model.put("message", "邮件信息!!");
        model.put("username", "用户");
        model.put("code", "2222");
        model.put("from", "官方");
        mailService.sendHtmlMail("dongyaofeng@nowledgedata.com", "邮件测试", model);
        return ResultUtil.success();
    }
}
