package com.wjn.mall.api.vo;

import lombok.Data;

@Data
public class SearchGoodsVO {

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String goodsCoverImg;

    private Integer sellingPrice;

}
