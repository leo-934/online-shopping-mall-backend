package com.wjn.mall.api.vo;

import lombok.Data;

@Data
public class UserAddressVO {

    private Long addressId;

    private Long userId;

    private String userName;

    private String userPhone;

    private Byte defaultFlag;

    private String provinceName;

    private String cityName;

    private String regionName;

    private String detailAddress;
}
