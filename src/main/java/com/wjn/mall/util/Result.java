package com.wjn.mall.util;

import com.wjn.mall.common.ResultMessageEnum;
import lombok.Data;

@Data
public class Result {

    private String message;

    private int resultCode;

    private Object data;

    private Result(String message,int resultCode,Object data){
        this.resultCode=resultCode;
        this.message=message;
        this.data=data;
    }

    /*public static Result getSuccessResult(String message){
        return new Result(message,200,null);
    }*/

    public static Result getSuccessResult(){
        return new Result ("success",200,null);
    }

    public static Result getSuccessResult(Object data){
        return new Result ("success",200,data);
    }

    /*public static Result getSuccessResult(String message, Object data){
        return new Result (message,200,data);
    }
*/

    public static Result getFailResult(String message){
        int code=500;
        if(message.equals(ResultMessageEnum.NO_LOGIN_ERROR.getMessage()))
            code=416;
        if(message.equals(ResultMessageEnum.LOGIN_EXPIRE_ERROR.getMessage()))
            code=416;
        if(message.equals(ResultMessageEnum.NO_USER_ERROR.getMessage()))
            code=416;

        return new Result(message,code,null);
    }

}
