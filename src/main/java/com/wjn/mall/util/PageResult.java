package com.wjn.mall.util;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<S> {

    private Integer totalCount;

    private Integer pageSize;

    private Integer totalPage;

    private Integer currPage;

    private List<S> list;

    public PageResult(List<S> list, Integer totalCount, Integer pageSize, Integer currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }
}
