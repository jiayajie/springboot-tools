package com.example.baseproject.controller;

import com.example.baseproject.service.LogSercive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/25.
 */
@RestController
public class LogsController {
    @Autowired
    private LogSercive logSercive;

    @GetMapping("/es")
    public String funName() {
        logSercive.saveLog();
        return "save - ok !";
    }
}
