package com.vins_nerf.core.utils;

import lombok.NonNull;

public class DistanceUtil {
    /**
     * 汉明距离
     *
     * @param x byte
     * @param y byte
     * @return distance
     */
    public static int hamming(byte x, byte y) {
        int s = x ^ y, ret = 0;  // 异或运算，结果初始化
        while (s != 0) {  // 循环条件
            ret += s & 1;  // 将个位和1做按位与运算
            s >>= 1;  // 右移一位，进行下一位的判断
        }
        return ret;  // 返回结果
    }

    /**
     * 汉明距离
     *
     * @param x byte[]
     * @param y byte[]
     * @return distance
     */
    public static int hamming(@NonNull byte[] x, @NonNull byte[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Byte Array's length isn't equal.");
        }

        int ret = 0;
        for (int i = 0; i < x.length; i++) {
            ret += hamming(x[i], y[i]);
        }
        return ret;
    }

}
