package com.wjn.mall.service.impl;

import com.wjn.mall.api.vo.UserAddressVO;
import com.wjn.mall.common.ResultMessageEnum;
import com.wjn.mall.config.exception.MallException;
import com.wjn.mall.dao.UserAddressMapper;
import com.wjn.mall.entity.UserAddress;
import com.wjn.mall.util.CustomBeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserAddressService {

    @Resource
    UserAddressMapper userAddressMapper;

    @Transactional
    public List<UserAddressVO> getAddressList(Long userId) throws MallException {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        List<UserAddress> userAddressList = userAddressMapper.select(userAddress);
        if(userAddressList==null || userAddressList.size() == 0)
            throw new MallException(ResultMessageEnum.DATABASE_ERROR.getMessage());
        return CustomBeanUtil.copyList(userAddressList,UserAddressVO.class);
    }

    @Transactional
    public UserAddressVO getDefaultAddress(Long userId) throws MallException {
        UserAddress userAddress = getDefaultAddressEntityById(userId);
        return CustomBeanUtil.copyProperties(userAddress,UserAddressVO.class);
    }

    private UserAddress getDefaultAddressEntityById(Long userId) throws MallException {
        UserAddress address = new UserAddress();
        address.setUserId(userId);
        address.setDefaultFlag((byte) 1);
        List<UserAddress> userAddressList = userAddressMapper.select(address);
        if(userAddressList==null || userAddressList.size() == 0 || userAddressList.size() > 1)
            throw new MallException(ResultMessageEnum.DATABASE_ERROR.getMessage());
        return userAddressList.get(0);
    }

    @Transactional
    public UserAddressVO getAddressById(Long addressId, Long userId) throws MallException {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setAddressId(addressId);
        List<UserAddress> userAddressList = userAddressMapper.select(userAddress);
        /*if(userAddressList==null || userAddressList.size() == 0)
            throw new MallException(ResultMessageEnum.DATABASE_ERROR.getMessage());*/
        return CustomBeanUtil.copyProperties(userAddressList.get(0),UserAddressVO.class);
    }

    @Transactional
    public void saveAddress(UserAddress userAddress) throws MallException {
        if(userAddress.getDefaultFlag()==1){
            UserAddress defaultAddress=getDefaultAddressEntityById(userAddress.getUserId());
            defaultAddress.setDefaultFlag((byte) 0);
            userAddressMapper.updateByAddressId(defaultAddress);
        }
        if(userAddressMapper.insert(userAddress)<=0)
            throw new MallException(ResultMessageEnum.DATABASE_ERROR.getMessage());
    }

    @Transactional
    public void updateAddress(UserAddress userAddress) throws MallException {
        if(userAddress.getDefaultFlag()==1){
            UserAddress defaultAddress=getDefaultAddressEntityById(userAddress.getUserId());
            if(defaultAddress.getAddressId()!=userAddress.getAddressId()) {
                defaultAddress.setDefaultFlag((byte) 0);
                userAddressMapper.updateByAddressId(defaultAddress);
            }
        }
        if(userAddressMapper.updateByAddressId(userAddress)<=0)
            throw new MallException(ResultMessageEnum.DATABASE_ERROR.getMessage());
    }

    public void deleteAddressByAddressId(Long addressId, Long userId) throws MallException {
        /*UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setAddressId(addressId);
        List<UserAddress> userAddressList = userAddressMapper.select(userAddress);
        if(userAddressList!=null && userAddressList.size() != 0) {

        }*/
        userAddressMapper.deleteByAddressIdAndUserId(addressId,userId);
    }

}
