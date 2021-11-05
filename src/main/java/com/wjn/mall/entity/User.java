package com.wjn.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long userId;

    private String nickName;

    private String loginName;

    private String passwordMd5;

    private String introduceSign;

    private Long isDeleted;

    private Long lockedFlag;

    @JsonFormat
    private Date createTime;
}
