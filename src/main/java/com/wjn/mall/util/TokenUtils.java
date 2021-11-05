package com.wjn.mall.util;

import com.wjn.mall.common.ResultMessageEnum;
import com.wjn.mall.config.exception.MallException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public abstract class TokenUtils {
    public static String getToken(Long userId) {
        Objects.requireNonNull(userId,"md5 userid null");
        String resource=""+System.currentTimeMillis()+userId+Math.random();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String md5Res = new BigInteger(1, md.digest(resource.getBytes())).toString(16);
            return md5Res;
        }
        catch(Exception e){
            throw new RuntimeException("md5 error");
        }
    }
}
