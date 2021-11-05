package com.wjn.mall.service.impl;

import com.wjn.mall.common.Constants;
import com.wjn.mall.common.ResultMessageEnum;
import com.wjn.mall.config.annotation.TokenUser;
import com.wjn.mall.config.exception.MallException;
import com.wjn.mall.dao.UserMapper;
import com.wjn.mall.dao.UserTokenMapper;
import com.wjn.mall.entity.User;
import com.wjn.mall.entity.UserToken;
import com.wjn.mall.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserTokenMapper userTokenMapper;

    @Transactional
    public void logout(Long userId){
        userTokenMapper.deleteByUserId(userId);
    }

    @Transactional
    public String login(String loginName, String passwordMd5) throws MallException {
        User selectUser = userMapper.selectByLoginName(loginName);
        if(!passwordMd5.equals(selectUser.getPasswordMd5())){
            throw new MallException(ResultMessageEnum.PASSWORD_NOT_CORRECT.getMessage());
        }

        if (selectUser == null) {
            throw new MallException(ResultMessageEnum.USER_NOT_EXIST.getMessage());
        }
        UserToken userToken = userTokenMapper.selectUserByUserId(selectUser.getUserId());

        if(userToken==null){
            userToken=new UserToken();
            Date now=new Date();
            Date expire=new Date(now.getTime()+2*24*3600*1000);
            userToken.setUserId(selectUser.getUserId());
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expire);
            userToken.setToken(TokenUtils.getToken(userToken.getUserId()));
            userTokenMapper.insertIntoUserToken(userToken);
        }
        else{
            Date now=new Date();
            Date expire=new Date(now.getTime()+2*24*3600*1000);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expire);
            userTokenMapper.updateUserByUserId(userToken);
        }
        return userToken.getToken();
    }

    @Transactional
    public void register(String loginName, String passwordMd5) throws MallException {
        User selectUser = userMapper.selectByLoginName(loginName);

        if (selectUser != null) {
            throw new MallException(ResultMessageEnum.LOGIN_NAME_ALREADY_EXIST.getMessage());
        }
        User user=new User();
        user.setLoginName(loginName);
        user.setPasswordMd5(passwordMd5);
        user.setNickName(loginName);
        user.setIntroduceSign(Constants.defaultIntroduceSign);
        if (userMapper.insertIntoUser(user) < 1) {
            throw new MallException(ResultMessageEnum.DATABASE_ERROR.getMessage());
        }
    }

    @Transactional
    public void update(User user) throws MallException {
        User selectUser = userMapper.selectByUserId(user.getUserId());
        if(selectUser == null){
            //实际上不可能为空，因为userId是由注解给出的，如果为空应该早就抛出异常了
            throw new MallException(ResultMessageEnum.USER_NOT_EXIST.getMessage());
        }
        userMapper.updateByUserId(user);
    }
}
