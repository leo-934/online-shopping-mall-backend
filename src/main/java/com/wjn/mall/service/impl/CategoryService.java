package com.wjn.mall.service.impl;

import com.wjn.mall.api.vo.IndexCategoryVO;
import com.wjn.mall.api.vo.SecondLevelCategoryVO;
import com.wjn.mall.api.vo.ThirdLevelCategoryVO;
import com.wjn.mall.dao.CategoryMapper;
import com.wjn.mall.entity.GoodsCategory;
import com.wjn.mall.util.CustomBeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Transactional
    public List<IndexCategoryVO> getCategories(){
        List<GoodsCategory> selectFirstCategories = categoryMapper.selectGoodsCategoryByParentIdsAndLevel(Collections.singletonList(0L),1L);

        List<IndexCategoryVO> resLevelOne=CustomBeanUtil.copyList(selectFirstCategories,IndexCategoryVO.class);

        List<Long> firstLevelIds = selectFirstCategories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
        List<GoodsCategory> selectSecondCategories = categoryMapper.selectGoodsCategoryByParentIdsAndLevel(firstLevelIds,2L);

        List<SecondLevelCategoryVO> resLevelTwo = CustomBeanUtil.copyList(selectSecondCategories, SecondLevelCategoryVO.class);

        List<Long> secondLevelIds = selectSecondCategories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
        List<GoodsCategory> selectThirdCategories = categoryMapper.selectGoodsCategoryByParentIdsAndLevel(secondLevelIds,3L);

        Map<Long, List<GoodsCategory>> mapLevelThree = selectThirdCategories.stream().collect(Collectors.groupingBy(GoodsCategory::getParentId));

        for(SecondLevelCategoryVO i : resLevelTwo){
            List<ThirdLevelCategoryVO> tmp = CustomBeanUtil.copyList(mapLevelThree.get(i.getCategoryId()),ThirdLevelCategoryVO.class);
            i.setThirdLevelCategoryVOS(tmp);
        }

        Map<Long, List<SecondLevelCategoryVO>> mapLevelTwo = resLevelTwo.stream().collect(Collectors.groupingBy(SecondLevelCategoryVO::getParentId));
        for(IndexCategoryVO i : resLevelOne){
            i.setSecondLevelCategoryVOS(mapLevelTwo.get(i.getCategoryId()));
        }
        return resLevelOne;
    }
}
