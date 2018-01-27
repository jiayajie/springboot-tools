package com.example.baseproject.common.enums;

/**
 * 自定义异常枚举类
 */
public enum ResultEnum {
    OK(200, "成功返回请求数据"),
    CREATED(201, "新建或修改数据成功"),
    ACCEPTED(202, "一个请求已经进入后台排队（异步任务）"),
    NO_CONTENT(204, "删除数据成功"),
    INVALID_REQUEST(400, "请求有错误"), //用户发出的请求有错误，服务器没有进行新建或修改数据的操作
    UNAUTHORIZE(401, "没有权限"), //表示用户没有权限（令牌、用户名、密码错误）
    NOT_FOUND(404, "请求不存在"), //用户发出的请求针对的是不存在的记录，服务器没有进行操作
    NOT_ACCEPTABLE(406, "格式不可得"), //用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）
    GONE(410, "资源被删除"), //用户请求的资源被永久删除，且不会再得到的
    UNPROCESABLE_ENTITY(422, ""), //当创建一个对象时，发生一个验证错误
    SERVER_ERROR(500, "服务器错误"), //用户将无法判断发出的请求是否成功

    DB_CLIENT(510, "数据库连接异常"),
    ES_CLIENT(511, "ES连接异常");


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
