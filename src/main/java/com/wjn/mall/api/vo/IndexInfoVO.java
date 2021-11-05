package com.wjn.mall.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class IndexInfoVO {

    private List<IndexCarouselVO> carousels;

    private List<IndexConfigGoodsVO> hotGoodses;

    private List<IndexConfigGoodsVO> newGoodses;

    private List<IndexConfigGoodsVO> recommendGoodses;
}
