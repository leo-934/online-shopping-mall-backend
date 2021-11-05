package com.wjn.mall.api.param;

import lombok.Data;

@Data
public class MallUserUpdateParam {

    private String nickName;

    private String passwordMd5;

    private String introduceSign;

}
