package com.example.baseproject.advice.exception.custom;

/**
 * 应用级别的错误
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/31 15:35
 * @Version 1.0
 */
public class AppException extends RuntimeException{

    private String viewPath;

    public AppException() {
        super();
    }
    public AppException(String message) {
        super(message);
        this.viewPath="error/error";
    }
    public AppException(String message,String viewPath) {
        super(message);
        this.viewPath=viewPath;
    }
    public String getViewPath(){
        return viewPath;
    }
}
