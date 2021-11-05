package com.wjn.mall.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomBeanUtil {
    public static void copyProperties(Object A, Object B){
        BeanUtils.copyProperties(A,B);
    }
    public static <T> T copyProperties(Object A, Class<T> clazz){
        T B = null;
        try {
            B= clazz.newInstance();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        BeanUtils.copyProperties(A,B);
        return B;
    }
    public static <S,T> List<T> copyList(List<S> A,Class<T> clazz) {
        List<T> B=new ArrayList<>();
        int i=0;
        try {
            for (S s : A) {
                T tmp = clazz.newInstance();
                BeanUtils.copyProperties(s, tmp);
                B.add(tmp);
                ++i;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return B;
    }
}
