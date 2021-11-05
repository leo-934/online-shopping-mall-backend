package com.wjn.mall.api.param;

import lombok.Data;

@Data
public class MallSaveOrderParam {

    private Long[] cartItemIds;

    private Long addressId;
}
