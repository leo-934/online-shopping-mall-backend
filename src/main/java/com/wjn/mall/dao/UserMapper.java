package com.wjn.mall.dao;

import com.wjn.mall.entity.User;

public interface UserMapper {

    User selectByUserId(Long userId);

    User selectByLoginName(String LoginName);

    int insertIntoUser(User user);

    int updateByUserId(User user);
}
