package com.vins_nerf.core.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtil {
    private final static Random RANDOM = new Random();
    private final static char[] CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * 判断字符串是否为null或空
     *
     * @param value 字符串
     * @return
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * 判断字符串数组中是否存在null或空
     *
     * @param values 字符串数组
     * @return
     */
    public static boolean haveNullOrEmpty(String... values) {
        return haveNullOrEmpty(Arrays.asList(values));
    }

    /**
     * 判断字符串数组是否为null或空
     *
     * @param values 字符串数组
     * @return
     */
    public static boolean haveNullOrEmpty(Collection<String> values) {
        if (values == null || values.isEmpty()) return false;

        for (String value : values) {
            if (isNullOrEmpty(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否是手机
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhone(String phoneNum) {
        if (StringUtil.isNullOrEmpty(phoneNum)) return false;

        Pattern pattern = Pattern.compile("1\\d{10}");
        return pattern.matcher(phoneNum.trim()).matches();
    }

    /**
     * 判断字符串是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (StringUtil.isNullOrEmpty(email)) return false;

        Pattern pattern = Pattern.compile("^[\\w-.]+@[\\w-]+(\\.{1}[\\w-]+)+$");
        return pattern.matcher(email.trim()).matches();
    }

    /**
     * 判断字符串是否是验证码（6位）
     *
     * @param phoneNum
     * @return
     */
    public static boolean isSMSCode(String phoneNum) {
        if (StringUtil.isNullOrEmpty(phoneNum)) return false;

        Pattern pattern = Pattern.compile("\\d{6}");
        return pattern.matcher(phoneNum.trim()).matches();
    }

    /**
     * 获取随机位数的字符串
     *
     * @param length 字符串长度
     * @param limit  随机字符的限度
     * @return
     */
    public static String getNonce(int length, int limit) {
        limit = Math.min(limit, CHARS.length);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(CHARS[RANDOM.nextInt(limit)]);
        }
        return sb.toString();
    }

    /**
     * 获取随机位数的字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String getNonce(int length) {
        return getNonce(length, CHARS.length);
    }

    /**
     * 获取随机数的字符串
     *
     * @param length
     * @return
     */
    public static String getSMSCode(int length) {
        return getNonce(length, 10);
    }

}
