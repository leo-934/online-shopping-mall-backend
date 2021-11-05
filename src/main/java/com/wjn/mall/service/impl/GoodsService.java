package com.wjn.mall.service.impl;

import com.wjn.mall.api.vo.SearchGoodsVO;
import com.wjn.mall.common.Constants;
import com.wjn.mall.dao.GoodsMapper;
import com.wjn.mall.entity.Goods;
import com.wjn.mall.util.CustomBeanUtil;
import com.wjn.mall.util.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Transactional
    public PageResult<SearchGoodsVO> selectByKeywordAndCategoryIdAndOrderAndPageNumber(String keyword, Long goodsCategoryId, String orderBy, Integer pageNumber){
        Integer offset=null;
        Integer limit=null;
        if(pageNumber!=null){
            offset= (pageNumber-1)* Constants.goodsSearchPageRecordNum;
            limit=Constants.goodsSearchPageRecordNum;
        }
        List<Goods> selectList =goodsMapper.selectByKeywordAndCategoryIdAndOrderAndLimit(keyword,goodsCategoryId,orderBy,offset,limit);
        List<SearchGoodsVO> resList = CustomBeanUtil.copyList(selectList,SearchGoodsVO.class);

        Integer totalCount= goodsMapper.selectCountByKeywordAndCategoryId(keyword,goodsCategoryId);

        return new PageResult<>(resList,totalCount,Constants.goodsSearchPageRecordNum,pageNumber);
    }

    public Goods getGoodsDetail(Long goodsId){
        return goodsMapper.selectByGoodsId(goodsId);
    }
}
