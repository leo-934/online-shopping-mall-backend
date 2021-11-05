package com.wjn.mall.config.handler;

import com.wjn.mall.common.ResultMessageEnum;
import com.wjn.mall.config.exception.MallException;
import com.wjn.mall.util.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.sql.SQLException;

@RestControllerAdvice
//MallException是一个用于反应外部异常的异常类，不是由程序bug引起，所以应该继承Exception而不是runtimeexception
public class MallExceptionHandler {

    @ExceptionHandler({BindException.class,MethodArgumentNotValidException.class})
    public Result handleParameterException(Exception e){
        e.printStackTrace();
        return Result.getFailResult(ResultMessageEnum.INVALID_PARAMETER_ERROR.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleAllException(Exception e){
        e.printStackTrace();
        return Result.getFailResult(ResultMessageEnum.UNKNOWN_ERROR.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public Result handleSQLException(Exception e){
        e.printStackTrace();
        return Result.getFailResult(ResultMessageEnum.DATABASE_ERROR.getMessage());
    }

    @ExceptionHandler(MallException.class)
    public Result handleMallException(MallException e){
        e.printStackTrace();
        return Result.getFailResult(e.getMessage());
    }
}
