package com.wjn.mall.common;

public enum IndexTypeEnum {

    HOT(3),
    NEW(4),
    RECOMMEND(5);

    private Integer type;

    IndexTypeEnum(Integer type){
        this.type=type;
    }

    public Integer getType() {
        return type;
    }

}
