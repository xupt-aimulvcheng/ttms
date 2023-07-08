package com.xupt.ttms.pojo;/*
 * @author:ck
 * @param:
 * @data:2022/7/8
 * @description:
 */

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -3554614540427758699L;
    private Integer code;
    private String message;
    private T data;
    private Long count;

    public Result(int code, String fail, T data) {
        this.code = code;
        this.message = fail;
        this.data = data;
    }

    public static Result success(Object result) {
        return new Result(200,"success",result,null);
    }

    public static Result fail(String message, Integer code) {
        return new Result(code,message,null,null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Result() {
    }

    public Result(Integer code, String message, T data, Long count) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result<Object> success() {
        return new Result(200,"success",null,null);
    }
    public static Result<Object> success(Object data,Long count) {
        return new Result(200,"success",data,count);
    }
    public static Result<Object> fail(Object data){return new Result<>(1,"fail",data);};
}
