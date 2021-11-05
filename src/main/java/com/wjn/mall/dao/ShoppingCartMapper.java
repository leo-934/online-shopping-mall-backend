package com.wjn.mall.dao;

import com.wjn.mall.entity.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartMapper {

    List<ShoppingCartItem> selectItemList(Integer offset, Integer limit, Long userId);

    Integer selectCount();

    ShoppingCartItem selectItemByItem(ShoppingCartItem shoppingCartItem);

    Integer updateByCartItemId(ShoppingCartItem shoppingCartItem);

    Integer insertItem(ShoppingCartItem shoppingCartItem);

    Integer deleteItemByCartItemId(Long cartItemId);
}
