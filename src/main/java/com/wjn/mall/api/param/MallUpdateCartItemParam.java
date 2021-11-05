package com.wjn.mall.api.param;

import lombok.Data;

@Data
public class MallUpdateCartItemParam {

    private Long cartItemId;

    private Integer goodsCount;
}
