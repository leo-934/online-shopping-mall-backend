package com.wjn.mall.service.impl;


import com.wjn.mall.api.vo.*;
import com.wjn.mall.dao.GoodsMapper;
import com.wjn.mall.dao.OrderMapper;
import com.wjn.mall.dao.ShoppingCartMapper;
import com.wjn.mall.entity.Goods;
import com.wjn.mall.entity.Order;
import com.wjn.mall.entity.OrderAddress;
import com.wjn.mall.entity.OrderItem;
import com.wjn.mall.util.CustomBeanUtil;
import com.wjn.mall.util.CustomRandomEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Transactional
    public OrderDetailVO getDetail(String orderNo,Long userId){
        return CustomBeanUtil.copyProperties(orderMapper.select(orderNo,userId,null,null).get(0),OrderDetailVO.class);
    }

    @Transactional
    public String saveOrder(List<ShoppingCartItemVO> itemVOList, UserAddressVO userAddressVO, Long userId){
        //List<Long> cartItemIdList = itemVOList.stream().map(ShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        List<Goods> goodsList = new ArrayList<>();
        int totalPrice=0;
        for(var item:itemVOList){
            goodsList.add(goodsMapper.selectByGoodsId(item.getGoodsId()));
            shoppingCartMapper.deleteItemByCartItemId(item.getCartItemId());
            totalPrice+=item.getSellingPrice()*item.getGoodsCount();
            orderMapper.insertOrderItem(CustomBeanUtil.copyProperties(item, OrderItem.class));
        }


        Order order = new Order();
        order.setOrderNo(CustomRandomEngine.getOrderNo());
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setExtraInfo("extrainfo");

        orderMapper.insertOrder(order);
        orderMapper.insertOrderAddress(CustomBeanUtil.copyProperties(userAddressVO, OrderAddress.class));
        orderMapper.insertOrderAddress(CustomBeanUtil.copyProperties(userAddressVO, OrderAddress.class));


        return order.getOrderNo();
    }
    @Transactional
    public List<OrderListVO> getOrderList(Integer offset, Integer limit, Long userId){
        List<Order> orderList= orderMapper.select(null,userId,offset,limit);
        List<OrderListVO> orderListVOList = new ArrayList<>();
        for(var i:orderList){
            orderListVOList.add(CustomBeanUtil.copyProperties(
                    i,OrderListVO.class
            ));
            orderListVOList.get(orderListVOList.size()-1).setOrderItemVOS(
                    CustomBeanUtil.copyList(orderMapper.selectOrderItem(i.getOrderId()), OrderItemVO.class)
            );


        }
        return orderListVOList;
    }

    @Transactional
    public Integer getOrderTotalNum(Long userId){
        Order order = new Order();
        order.setUserId(userId);
        return orderMapper.selectNum(order);
    }

    @Transactional
    public void cancelOrder(String orderNo, Long userId){
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setOrderStatus((byte) 6);

        orderMapper.update(order);
    }

    @Transactional
    public void finishOrder(String orderNo, Long userId){
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setOrderStatus((byte) 5);

        orderMapper.update(order);
    }
}
