package com.example.baseproject.advice.exception;

import com.example.baseproject.common.model.ResultEntity;
import com.example.baseproject.common.enums.ResultEnum;
import com.example.baseproject.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created With IDEA.
 * 异常页面控制
 *
 * @author dongyaofeng
 * @date 2017/8/7
 * @time 9:09
 */
//@ControllerAdvice
@RestControllerAdvice //控制器增强
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    //@ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultEntity handle(Exception e) {

        //自定义异常
        if (e instanceof AudienceException) {
            AudienceException ep = (AudienceException) e;
            logger.error("[AudienceException]: {}", e);
            return ResultUtil.error(ep.getCode(), ep.getMessage());
        }

      /*  if( e instanceof  otherException) {
            //做一些事情
        }*/

        logger.error("[SysError]={}", e);
        return ResultUtil.error(ResultEnum.SERVER_ERROR.getCode(), "系统繁忙,请稍后再试");
    }
}
