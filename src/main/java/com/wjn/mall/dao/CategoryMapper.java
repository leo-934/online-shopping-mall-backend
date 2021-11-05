package com.wjn.mall.dao;

import com.wjn.mall.entity.GoodsCategory;

import java.util.List;

public interface CategoryMapper {
    List<GoodsCategory> selectGoodsCategoryByParentIdsAndLevel(List<Long> parentIds,Long level);
}
