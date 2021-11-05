package com.wjn.mall.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserToken {
    private Long UserId;
    private String token;
    private Date updateTime;
    private Date expireTime;
}
