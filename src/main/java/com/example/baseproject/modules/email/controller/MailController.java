package com.example.baseproject.modules.email.controller;

import com.example.baseproject.common.model.ResultEntity;
import com.example.baseproject.modules.email.service.MailService;
import com.example.baseproject.modules.redis.service.RedisTestService;
import com.example.baseproject.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MailController {


    @Autowired
    MailService mailService;


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
