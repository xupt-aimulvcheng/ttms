package com.xupt.ttms.config.exception;/*
 * @author ck
 * @date 2023/3/6
 * //@ApiNote
 */

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(20000,"成功"),
    FAIL(20001, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    PARAM_ERROR(205, "参数异常"),
    LOGIN_ERROR(206, "登陆异常"),
    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限");

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
