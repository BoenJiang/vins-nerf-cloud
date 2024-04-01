package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.core.valid.RestEmailFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RestEmailValidator implements ConstraintValidator<RestEmailFormat, String> {
    private Set<String> whiteList, blackList;

    @Override
    public void initialize(RestEmailFormat restEmailFormat) {
        this.whiteList = new HashSet<>(Arrays.asList(restEmailFormat.whiteList()));
        this.blackList = new HashSet<>(Arrays.asList(restEmailFormat.blackList()));
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 如果邮件格式不对，则返回false；
        if (!StringUtil.isEmail(s)) return false;

        // 如果邮件在黑名单中，则返回false；
        if (this.blackList.contains(s)) return false;

        // 如果白名单非空，则认为白名单启效果，非白名单用户返回false；
        return this.whiteList.isEmpty() ? true : this.whiteList.contains(s);
    }
}
