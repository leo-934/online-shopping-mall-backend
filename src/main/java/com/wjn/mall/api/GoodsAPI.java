package com.wjn.mall.api;

import com.wjn.mall.api.vo.GoodsDetailVO;
import com.wjn.mall.api.vo.SearchGoodsVO;
import com.wjn.mall.common.ResultMessageEnum;
import com.wjn.mall.config.annotation.TokenUser;
import com.wjn.mall.config.exception.MallException;
import com.wjn.mall.entity.Goods;
import com.wjn.mall.entity.User;
import com.wjn.mall.service.impl.GoodsService;
import com.wjn.mall.util.CustomBeanUtil;
import com.wjn.mall.util.PageResult;
import com.wjn.mall.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1")
public class GoodsAPI {

    @Resource
    private GoodsService goodsService;

    @GetMapping("/search")
    public Result search(@RequestParam(required = false) String keyword,
                         @RequestParam(required = false) Long goodsCategoryId,
                         @RequestParam(required = false) String orderBy,
                         @RequestParam(required = false) Integer pageNumber,
                         @TokenUser User user) throws MallException {

        if((keyword==null||keyword.isBlank())&&goodsCategoryId==null)
            throw new MallException(ResultMessageEnum.INVALID_PARAMETER.getMessage());
        if(pageNumber<=0)
            throw new MallException(ResultMessageEnum.INVALID_PARAMETER.getMessage());
        PageResult<SearchGoodsVO> res = goodsService.selectByKeywordAndCategoryIdAndOrderAndPageNumber(keyword,goodsCategoryId,orderBy,pageNumber);

        return Result.getSuccessResult(res);
    }

    @GetMapping("/goods/detail/{goodsId}")
    public Result getGoodsDetail(@PathVariable("goodsId") Long goodsId , @TokenUser User user) throws MallException {
        if(goodsId == null ||goodsId <0)
            throw new MallException(ResultMessageEnum.INVALID_PARAMETER.getMessage());

        Goods selectRes= goodsService.getGoodsDetail(goodsId);
        GoodsDetailVO res =new GoodsDetailVO();
        CustomBeanUtil.copyProperties(selectRes,res);
        return Result.getSuccessResult(res);
    }


}
