package com.wjn.mall.util;

import com.wjn.mall.common.ResultMessageEnum;
import com.wjn.mall.config.exception.MallException;

public abstract class CheckUtils {

    public static void requireNotNull(Object object) throws MallException {
        if(object==null){
            throw new MallException(ResultMessageEnum.INVALID_PARAMETER.getMessage());
        }
    }

    public static void requireStringContent(String str) throws MallException {
        if(str==null||str.isBlank()){
            throw new MallException(ResultMessageEnum.INVALID_PARAMETER.getMessage());
        }
    }
}
