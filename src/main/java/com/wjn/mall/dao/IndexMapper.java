package com.wjn.mall.dao;

import com.wjn.mall.entity.Carousel;

import java.util.List;

public interface IndexMapper {

    List<Carousel> selectCarouselByLimit(Integer limit);

    List<Long> selectGoodsIdByTypeAndLimit(Integer type, Integer limit);
}
