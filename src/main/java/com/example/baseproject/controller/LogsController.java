package com.example.baseproject.controller;

import com.example.baseproject.domain.ResultEntity;
import com.example.baseproject.service.LogSercive;
import com.example.baseproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongyaofeng on 2017/12/25.
 */
@RestController
public class LogsController {
    @Autowired
    private LogSercive logSercive;

    @GetMapping("/")
    public ResultEntity funName() {
        logSercive.saveLog();
        return ResultUtil.success();
    }
}
