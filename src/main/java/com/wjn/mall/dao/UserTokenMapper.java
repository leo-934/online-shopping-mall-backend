package com.wjn.mall.dao;

import com.wjn.mall.entity.User;
import com.wjn.mall.entity.UserToken;

public interface UserTokenMapper {
    UserToken selectUserByToken(String token);

    UserToken selectUserByUserId(Long userId);

    int updateUserByUserId(UserToken userToken);

    int insertIntoUserToken(UserToken userToken);

    int deleteByUserId(Long userId);
}
