package com.wjn.mall.service.impl;

import com.wjn.mall.api.vo.ShoppingCartItemVO;
import com.wjn.mall.common.ResultMessageEnum;
import com.wjn.mall.config.exception.MallException;
import com.wjn.mall.dao.GoodsMapper;
import com.wjn.mall.dao.ShoppingCartMapper;
import com.wjn.mall.entity.ShoppingCartItem;
import com.wjn.mall.util.CustomBeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartService {

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Transactional
    public List<ShoppingCartItemVO> getItemPageList(Integer offset, Integer limit, Long userId){
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartMapper.selectItemList(offset,limit, userId);
        return getShoppingCartItemVOS(shoppingCartItemList);
    }


    @Transactional
    public List<ShoppingCartItemVO> getItemsByItemIds(List<Long> itemIds, Long userId) throws MallException {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setUserId(userId);
        List<ShoppingCartItem> shoppingCartItemList = new ArrayList<>();
        for (Long id:itemIds) {
            shoppingCartItem.setCartItemId(id);
            var item=shoppingCartMapper.selectItemByItem(shoppingCartItem);
            if(item ==null){
                throw new MallException(ResultMessageEnum.INVALID_PARAMETER.getMessage());
            }
            shoppingCartItemList.add(item);
        }
        return getShoppingCartItemVOS(shoppingCartItemList);
    }

    private List<ShoppingCartItemVO> getShoppingCartItemVOS(List<ShoppingCartItem> shoppingCartItemList) {
        List<ShoppingCartItemVO> res = new ArrayList<>();
        for (ShoppingCartItem shoppingCartItem:shoppingCartItemList) {
            ShoppingCartItemVO tmp=CustomBeanUtil.copyProperties(goodsMapper.selectByGoodsId(shoppingCartItem.getGoodsId()),ShoppingCartItemVO.class);
            tmp.setCartItemId(shoppingCartItem.getCartItemId());
            tmp.setGoodsCount(shoppingCartItem.getGoodsCount());
            res.add(tmp);
        }
        return res;
    }

    @Transactional
    public Integer getItemTotalNumber(){
        return shoppingCartMapper.selectCount();
    }

    @Transactional
    public void saveItem(ShoppingCartItem shoppingCartItem) throws MallException {
        if(goodsMapper.selectByGoodsId(shoppingCartItem.getGoodsId())==null)
            throw new MallException(ResultMessageEnum.INVALID_PARAMETER_ERROR.getMessage());

        Integer goodsCount=shoppingCartItem.getGoodsCount();
        shoppingCartItem.setGoodsCount(null);
        ShoppingCartItem selectRes = shoppingCartMapper.selectItemByItem(shoppingCartItem);
        if(selectRes!=null)
            throw new MallException(ResultMessageEnum.SHOPPING__ITEM_ALREADY_EXIST.getMessage());
        shoppingCartItem.setGoodsCount(goodsCount);
        if(shoppingCartMapper.insertItem(shoppingCartItem)<=0)
            throw new MallException(ResultMessageEnum.DATABASE_ERROR.getMessage());
    }


    @Transactional
    public void updateItem(ShoppingCartItem shoppingCartItem) throws MallException {
        Integer goodsCount=shoppingCartItem.getGoodsCount();
        shoppingCartItem.setGoodsCount(null);
        ShoppingCartItem selectRes = shoppingCartMapper.selectItemByItem(shoppingCartItem);

        if(selectRes==null)
            throw new MallException(ResultMessageEnum.NO_SUCH_SHOPPINGCART_ITEM.getMessage());

        selectRes.setGoodsCount(goodsCount);
        selectRes.setUpdateTime(new Date());
        if(shoppingCartMapper.updateByCartItemId(selectRes)<=0)
            throw new MallException(ResultMessageEnum.DATABASE_ERROR.getMessage());
    }

    @Transactional
    public void deleteItem(ShoppingCartItem shoppingCartItem) throws MallException {
        ShoppingCartItem selectRes = shoppingCartMapper.selectItemByItem(shoppingCartItem);
        if(selectRes!=null){
            if(shoppingCartMapper.deleteItemByCartItemId(shoppingCartItem.getCartItemId())<=0){
                throw new MallException(ResultMessageEnum.DATABASE_ERROR.getMessage());
            }
        }

    }

}
