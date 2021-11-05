package com.wjn.mall.config.handler;

import com.wjn.mall.common.ResultMessageEnum;
import com.wjn.mall.config.annotation.TokenUser;
import com.wjn.mall.config.exception.MallException;
import com.wjn.mall.dao.UserMapper;
import com.wjn.mall.dao.UserTokenMapper;
import com.wjn.mall.entity.User;
import com.wjn.mall.entity.UserToken;
import com.wjn.mall.util.Result;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

@Component
public class TokenUserAnnotationHandler implements HandlerMethodArgumentResolver {

    @Resource
    UserMapper userMapper;

    @Resource
    UserTokenMapper userTokenMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if(parameter.getParameterAnnotation(TokenUser.class)!=null){
            String token= webRequest.getHeader("token");
            if(token!=null&&!token.isEmpty()){
                UserToken userToken=userTokenMapper.selectUserByToken(token);
                if(userToken!=null&&userToken.getExpireTime().getTime()>System.currentTimeMillis()){
                    User user=userMapper.selectByUserId(userToken.getUserId());//select by userid
                    if(user!=null)
                        return user;
                    else{
                        throw new MallException(ResultMessageEnum.NO_USER_ERROR.getMessage());
                    }
                }
                else{
                    throw new MallException(ResultMessageEnum.LOGIN_EXPIRE_ERROR.getMessage());
                }
            }
            else{
                throw new MallException(ResultMessageEnum.NO_LOGIN_ERROR.getMessage());
            }
        }
        return null;
    }
}
