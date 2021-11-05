package com.wjn.mall.api;

import com.wjn.mall.service.impl.CategoryService;
import com.wjn.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1")
public class CategoryAPI {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public Result getCategories(){
        return Result.getSuccessResult(categoryService.getCategories());
    }
}
