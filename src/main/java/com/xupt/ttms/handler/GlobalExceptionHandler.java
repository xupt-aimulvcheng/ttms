package com.xupt.ttms.handler;

import com.xupt.ttms.config.exception.CkException;
import com.xupt.ttms.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //全局异常处理
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }


    @ExceptionHandler(CkException.class)
    public Result error(CkException e){
        e.printStackTrace();
        return Result.fail(e.getMessage(),e.getCode());
    }
}
