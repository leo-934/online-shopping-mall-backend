package com.wjn.mall.config.exception;

public class MallException extends Exception{

    String message;

    public MallException(String message){
        super(message);
    }

    public MallException(int resultCode,String message){
        super(message);
    }

}
