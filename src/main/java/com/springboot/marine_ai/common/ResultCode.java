package com.springboot.marine_ai.common;

public enum ResultCode {
    SUCCESS(200, "操作成功"),
    PARAM_ERROR(400, "参数异常"),
    NOT_FOUND(404, "资源不存在"),
    SERVER_ERROR(500, "服务器异常");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}