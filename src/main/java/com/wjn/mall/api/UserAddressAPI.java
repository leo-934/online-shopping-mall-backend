 package com.wjn.mall.api;

import com.wjn.mall.api.param.MallSaveUserAddressParam;
import com.wjn.mall.api.param.MallUpdateUserAddressParam;
import com.wjn.mall.config.annotation.TokenUser;
import com.wjn.mall.config.exception.MallException;
import com.wjn.mall.entity.User;
import com.wjn.mall.entity.UserAddress;
import com.wjn.mall.service.impl.UserAddressService;
import com.wjn.mall.util.CustomBeanUtil;
import com.wjn.mall.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1")
public class UserAddressAPI {

    @Resource
    private UserAddressService userAddressService;

    @GetMapping("/address")
    public Result getList(@TokenUser User user) throws MallException {
        return Result.getSuccessResult(userAddressService.getAddressList(user.getUserId()));
    }

    @PostMapping("/address")
    public Result saveAddress(@RequestBody MallSaveUserAddressParam mallSaveUserAddressParam, @TokenUser User user) throws MallException {
        UserAddress userAddress = CustomBeanUtil.copyProperties(mallSaveUserAddressParam, UserAddress.class);
        userAddress.setUserId(user.getUserId());
        userAddressService.saveAddress(userAddress);
        return Result.getSuccessResult();

    }

    @PutMapping("/address")
    public Result updateAddress(@RequestBody MallUpdateUserAddressParam mallUpdateUserAddressParam, @TokenUser User user) throws MallException {
        UserAddress userAddress = CustomBeanUtil.copyProperties(mallUpdateUserAddressParam, UserAddress.class);
        userAddress.setUserId(user.getUserId());
        userAddressService.updateAddress(userAddress);
        return Result.getSuccessResult();
    }

    @GetMapping("/address/{addressId}")
    public Result getAddressById(@PathVariable("addressId") Long addressId,@TokenUser User user) throws MallException {
        return Result.getSuccessResult(userAddressService.getAddressById(addressId,user.getUserId()));
    }

    @GetMapping("/address/default")
    public Result getDefaultAddressById(@TokenUser User user) throws MallException {
        return Result.getSuccessResult(userAddressService.getDefaultAddress(user.getUserId()));
    }

    @DeleteMapping("/address/{addressId}")
    public Result deleteAddressById(@PathVariable("addressId") Long addressId,@TokenUser User user) throws MallException {
        userAddressService.deleteAddressByAddressId(addressId, user.getUserId());
        return Result.getSuccessResult();
    }
}
