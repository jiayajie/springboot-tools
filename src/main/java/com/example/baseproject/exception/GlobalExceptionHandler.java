package com.example.baseproject.exception;

import com.example.baseproject.domain.ResultEntity;
import com.example.baseproject.enums.ResultEnum;
import com.example.baseproject.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created With IDEA.
 * 异常页面控制
 *
 * @author dongyaofeng
 * @date 2017/8/7
 * @time 9:09
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultEntity handle(Exception e) {

        //自定义异常
        if (e instanceof AudienceException) {
            AudienceException ep = (AudienceException) e;
            logger.error("[AudienceException]: {}", e);
            return ResultUtil.error(ep.getCode(), ep.getMessage());
        }
        logger.error("[SysError]={}", e);
        return ResultUtil.error(ResultEnum.SERVER_ERROR.getCode(), "系统繁忙,请稍后再试");
    }
}
