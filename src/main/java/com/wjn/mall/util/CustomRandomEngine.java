package com.wjn.mall.util;

public abstract class CustomRandomEngine {
    public static String getOrderNo() {
        StringBuffer buffer = new StringBuffer(String.valueOf(System.currentTimeMillis()));
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < 4; i++) {
            num = num * 10;
        }
        buffer.append((int) ((random * num)));
        return buffer.toString();
    }
}
