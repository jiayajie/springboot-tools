package com.example.baseproject.advice.exception.custom;

/**
 * Created With IDEA.
 * URL攻击 异常
 * @author dongyaofeng
 * @date 2018/1/30 13:26
 */
public class RequestLimitException extends RuntimeException{

    public RequestLimitException() {
        super("HTTP请求超出设定的限制");
    }

    public RequestLimitException(String localAddr) {
        super(localAddr);
    }
}
