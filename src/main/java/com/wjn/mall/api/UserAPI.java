package com.wjn.mall.api;

import com.wjn.mall.api.param.MallUserLoginParam;
import com.wjn.mall.api.param.MallUserRegisterParam;
import com.wjn.mall.api.param.MallUserUpdateParam;
import com.wjn.mall.api.vo.UserVO;
import com.wjn.mall.config.annotation.TokenUser;
import com.wjn.mall.config.exception.MallException;
import com.wjn.mall.entity.User;
import com.wjn.mall.service.impl.UserService;
import com.wjn.mall.util.CheckUtils;
import com.wjn.mall.util.CustomBeanUtil;
import com.wjn.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserAPI {

    @Autowired
    private UserService userService;

    @PostMapping("/logout")
    public Result logout(@TokenUser User user){
        userService.logout(user.getUserId());
        return Result.getSuccessResult();
    }

    @PostMapping("/login")
    public Result login(@RequestBody MallUserLoginParam mallUserLoginParam) throws MallException {
        CheckUtils.requireNotNull(mallUserLoginParam);
        CheckUtils.requireStringContent(mallUserLoginParam.getLoginName());
        CheckUtils.requireStringContent(mallUserLoginParam.getPasswordMd5());
        String token = userService.login(mallUserLoginParam.getLoginName(), mallUserLoginParam.getPasswordMd5());
        return Result.getSuccessResult(token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody MallUserRegisterParam mallUserRegisterParam) throws MallException {
        //copyproperties
        //to do md5
        CheckUtils.requireNotNull(mallUserRegisterParam);
        CheckUtils.requireStringContent(mallUserRegisterParam.getLoginName());
        CheckUtils.requireStringContent(mallUserRegisterParam.getPassword());
        userService.register(mallUserRegisterParam.getLoginName(), mallUserRegisterParam.getPassword());
        return Result.getSuccessResult();
    }

    @PutMapping("/info")
    public Result updateInfo(@RequestBody MallUserUpdateParam mallUserUpdateParam, @TokenUser User user) throws MallException {
        CheckUtils.requireNotNull(mallUserUpdateParam);
        CustomBeanUtil.copyProperties(mallUserUpdateParam,user);
        userService.update(user);
        return Result.getSuccessResult();
    }

    @GetMapping("/info")
    public Result getUserInfo(@TokenUser User user){
        UserVO res = new UserVO();
        CustomBeanUtil.copyProperties(user,res);
        return Result.getSuccessResult(res);
    }
}
