package com.wjn.mall.dao;

import com.wjn.mall.entity.UserAddress;

import java.util.List;

public interface UserAddressMapper {

    List<UserAddress> select(UserAddress userAddress);

    Integer updateByAddressId(UserAddress userAddress);

    Integer insert(UserAddress userAddress);

    Integer deleteByAddressIdAndUserId(Long addressId, Long userId);
}
