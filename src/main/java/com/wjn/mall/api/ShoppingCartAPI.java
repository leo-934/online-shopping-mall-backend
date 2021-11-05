package com.wjn.mall.api;

import com.wjn.mall.api.param.MallSaveCartItemParam;
import com.wjn.mall.api.param.MallUpdateCartItemParam;
import com.wjn.mall.api.vo.ShoppingCartItemVO;
import com.wjn.mall.common.Constants;
import com.wjn.mall.common.ResultMessageEnum;
import com.wjn.mall.config.annotation.TokenUser;
import com.wjn.mall.config.exception.MallException;
import com.wjn.mall.entity.ShoppingCartItem;
import com.wjn.mall.entity.User;
import com.wjn.mall.service.impl.ShoppingCartService;
import com.wjn.mall.util.CustomBeanUtil;
import com.wjn.mall.util.PageResult;
import com.wjn.mall.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShoppingCartAPI {

    @Resource
    private ShoppingCartService shoppingCartService;

    @GetMapping("/shop-cart/page")
    public Result getItemPageList(Integer pageNumber, @TokenUser User user) throws MallException {

        if(pageNumber<=0)
            throw new MallException(ResultMessageEnum.INVALID_PARAMETER.getMessage());

        Integer offset = (pageNumber-1)* Constants.shoppingCartPageRecordNum;
        Integer limit = Constants.shoppingCartPageRecordNum;
        List<ShoppingCartItemVO> shoppingCartItemList = shoppingCartService.getItemPageList(offset,limit, user.getUserId());
        return Result.getSuccessResult(
                new PageResult<ShoppingCartItemVO>(shoppingCartItemList, shoppingCartService.getItemTotalNumber(), Constants.shoppingCartPageRecordNum, pageNumber)
        );
    }

    @GetMapping("/shop-cart")
    public Result getItemList(@TokenUser User user){
        List<ShoppingCartItemVO> shoppingCartItemList = shoppingCartService.getItemPageList(null,null, user.getUserId());
        return Result.getSuccessResult(shoppingCartItemList);
    }

    @PostMapping("shop-cart")
    public Result saveItem(@RequestBody MallSaveCartItemParam mallSaveCartItemParam, @TokenUser User user) throws MallException {
        if(mallSaveCartItemParam.getGoodsCount()<=0||mallSaveCartItemParam.getGoodsId()<0)
            throw new MallException(ResultMessageEnum.INVALID_PARAMETER.getMessage());

        ShoppingCartItem shoppingCartItem = CustomBeanUtil.copyProperties(mallSaveCartItemParam, ShoppingCartItem.class);
        shoppingCartItem.setUserId(user.getUserId());

        shoppingCartService.saveItem(shoppingCartItem);

        return Result.getSuccessResult();
    }

    @PutMapping("/shop-cart")
    public Result updateItem(@RequestBody MallUpdateCartItemParam mallUpdateCartItemParam, @TokenUser User user) throws MallException {
        if(mallUpdateCartItemParam.getGoodsCount()<=0||mallUpdateCartItemParam.getCartItemId()<0)
            throw new MallException(ResultMessageEnum.INVALID_PARAMETER.getMessage());

        ShoppingCartItem shoppingCartItem = CustomBeanUtil.copyProperties(mallUpdateCartItemParam, ShoppingCartItem.class);
        shoppingCartItem.setUserId(user.getUserId());

        shoppingCartService.updateItem(shoppingCartItem);

        return Result.getSuccessResult();
    }

    @DeleteMapping("/shop-cart/{shoppingCartItemId}")
    public Result deleteItem(@PathVariable("shoppingCartItemId") Long itemId, @TokenUser User user) throws MallException {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setCartItemId(itemId);
        shoppingCartItem.setUserId(user.getUserId());
        shoppingCartService.deleteItem(shoppingCartItem);
        return Result.getSuccessResult();
    }

    @GetMapping("/shop-cart/settle")
    public Result getItemsByItemIds(Long[] itemIds, @TokenUser User user) throws MallException {
        List<Long> idList = new ArrayList<>();
        Collections.addAll(idList, itemIds);
        return Result.getSuccessResult(shoppingCartService.getItemsByItemIds(idList, user.getUserId()));
    }
}
