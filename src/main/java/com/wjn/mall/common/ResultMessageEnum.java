package com.wjn.mall.common;

public enum ResultMessageEnum {

    DEFAULT_SUCCESS_MESSAGE("success"),
    DEFAULT_FAIL_MESSAGE("fail"),
    INVALID_PARAMETER_ERROR("invalid request parameters"),
    UNKNOWN_ERROR("unknown error,please contact with the system administrator"),
    NO_LOGIN_ERROR("not login"),
    LOGIN_EXPIRE_ERROR("login expire"),
    LOGIN_NAME_ALREADY_EXIST("login name already exists"),
    USER_NOT_EXIST("user not exist"),
    INVALID_PARAMETER("invalid parameter"),
    NO_USER_ERROR("no user"),
    PASSWORD_NOT_CORRECT("the password is not correct"),
    SHOPPING__ITEM_ALREADY_EXIST("this item already exist"),
    NO_SUCH_ADDRESS_ERROR("no this address id"),
    NO_SUCH_SHOPPINGCART_ITEM("no such shopping cart item"),
    DATABASE_ERROR("database operation error");

    private String message;

    ResultMessageEnum(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

}
