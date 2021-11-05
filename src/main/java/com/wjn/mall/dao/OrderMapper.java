package com.wjn.mall.dao;

import com.wjn.mall.entity.Order;
import com.wjn.mall.entity.OrderAddress;
import com.wjn.mall.entity.OrderItem;

import java.util.List;

public interface OrderMapper {

    Integer insertOrder(Order order);

    Integer insertOrderItem(OrderItem orderItem);

    Integer insertOrderAddress(OrderAddress orderAddress);

    List<Order> select(String orderNo,Long userId,  Integer offset, Integer limit);


    List<OrderItem> selectOrderItem(long orderId);

    Integer selectNum(Order order);

    Integer update(Order order);
}
