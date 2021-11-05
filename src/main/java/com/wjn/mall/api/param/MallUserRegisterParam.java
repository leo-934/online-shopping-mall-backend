package com.wjn.mall.api.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MallUserRegisterParam {

    @NotEmpty(message = "login name can not be empty")
    private String loginName;

    @NotEmpty(message = "password can not be empty")
    private String password;
}
