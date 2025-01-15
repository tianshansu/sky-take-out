package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * handle business exceptions
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("Exception：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * handle sql exceptions
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" "); //split exception message by space
            String username = split[2]; //get the third string(username)
            String msg=username+MessageConstant.ALREADY_EXISTS;
            return Result.error(msg); //return the error message to frontend
        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

    }

}
