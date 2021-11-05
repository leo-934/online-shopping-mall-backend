package com.wjn.mall.api.vo;

import lombok.Data;

@Data
public class ThirdLevelCategoryVO {

    private Long categoryId;

    private Byte categoryLevel;

    private String categoryName;
}
