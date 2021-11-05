package com.wjn.mall.service.impl;

import com.wjn.mall.common.IndexTypeEnum;
import com.wjn.mall.dao.GoodsMapper;
import com.wjn.mall.dao.IndexMapper;
import com.wjn.mall.entity.Carousel;
import com.wjn.mall.entity.Goods;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndexService {

    @Resource
    IndexMapper indexMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Transactional
    public List<Carousel> getCarousel(Integer limit){
        return indexMapper.selectCarouselByLimit(limit);
    }

    @Transactional
    public List<Goods> getIndex(IndexTypeEnum type, Integer limit){
        List<Goods> res=new ArrayList<>();
        List<Long> goodsIds = indexMapper.selectGoodsIdByTypeAndLimit(type.getType(), limit);
        for (Long goodsId:goodsIds
             ) {
            res.add(goodsMapper.selectByGoodsId(goodsId));
        }
        return res;
    }
}
