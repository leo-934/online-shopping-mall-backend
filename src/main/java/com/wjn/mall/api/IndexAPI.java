package com.wjn.mall.api;

import com.wjn.mall.api.vo.IndexCarouselVO;
import com.wjn.mall.api.vo.IndexConfigGoodsVO;
import com.wjn.mall.api.vo.IndexInfoVO;
import com.wjn.mall.common.Constants;
import com.wjn.mall.common.IndexTypeEnum;
import com.wjn.mall.entity.Carousel;
import com.wjn.mall.entity.Goods;
import com.wjn.mall.entity.Index;
import com.wjn.mall.service.impl.IndexService;
import com.wjn.mall.util.CustomBeanUtil;
import com.wjn.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class IndexAPI {

    @Autowired
    IndexService indexService;

    @GetMapping("/index-infos")
    public Result getIndexInfos(){
        IndexInfoVO indexInfoVO = new IndexInfoVO();
        List<Carousel> carousel = indexService.getCarousel(Constants.carouselNum);
        List<Goods> hotIndex = indexService.getIndex(IndexTypeEnum.HOT,Constants.indexHotGoodsNum);
        List<Goods> newIndex = indexService.getIndex(IndexTypeEnum.NEW,Constants.indexNewGoodsNum);
        List<Goods> recommendIndex = indexService.getIndex(IndexTypeEnum.RECOMMEND,Constants.indexRecommendGoodsNum);
        indexInfoVO.setCarousels(CustomBeanUtil.copyList(carousel,IndexCarouselVO.class));
        indexInfoVO.setHotGoodses(CustomBeanUtil.copyList(hotIndex,IndexConfigGoodsVO.class));
        indexInfoVO.setNewGoodses(CustomBeanUtil.copyList(newIndex,IndexConfigGoodsVO.class));
        indexInfoVO.setRecommendGoodses(CustomBeanUtil.copyList(recommendIndex,IndexConfigGoodsVO.class));

        return Result.getSuccessResult(indexInfoVO);
    }
}
