package com.wjn.mall.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class IndexCategoryVO {

    private Long categoryId;

    private Byte categoryLevel;

    private String categoryName;

    private List<SecondLevelCategoryVO> secondLevelCategoryVOS;
}
