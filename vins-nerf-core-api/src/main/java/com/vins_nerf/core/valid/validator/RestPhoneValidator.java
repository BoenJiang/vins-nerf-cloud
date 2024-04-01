package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.core.valid.RestPhoneFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RestPhoneValidator implements ConstraintValidator<RestPhoneFormat, String> {
    private Set<String> whiteList, blackList;

    @Override
    public void initialize(RestPhoneFormat restPhoneFormat) {
        this.whiteList = new HashSet<>(Arrays.asList(restPhoneFormat.whiteList()));
        this.blackList = new HashSet<>(Arrays.asList(restPhoneFormat.blackList()));
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 如果手机格式不对，则返回false；
        if (!StringUtil.isPhone(s)) return false;

        // 如果手机在黑名单中，则返回false；
        if (this.blackList.contains(s)) return false;

        // 如果白名单非空，则认为白名单启效果，非白名单用户返回false；
        return this.whiteList.isEmpty() ? true : this.whiteList.contains(s);
    }
}
