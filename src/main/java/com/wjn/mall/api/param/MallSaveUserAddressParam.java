package com.wjn.mall.api.param;

import lombok.Data;

@Data
public class MallSaveUserAddressParam {

    private String userName;

    private String userPhone;

    private Byte defaultFlag;

    private String provinceName;

    private String cityName;

    private String regionName;

    private String detailAddress;
}
