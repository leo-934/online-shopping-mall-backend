package com.wjn.mall.dao;

import com.wjn.mall.entity.Goods;

import java.util.List;

public interface GoodsMapper {

    List<Goods> selectByKeywordAndCategoryIdAndOrderAndLimit(String keyword, Long goodsCategoryId, String orderBy, Integer offset, Integer limit);

    Integer selectCountByKeywordAndCategoryId(String keyword, Long goodsCategoryId);

    Goods selectByGoodsId(Long goodsId);
}
