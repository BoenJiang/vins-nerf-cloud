package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.core.valid.RestPasswordFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RestPasswordValidator implements ConstraintValidator<RestPasswordFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !StringUtil.isNullOrEmpty(value) && value.length() >= 8;
    }
}
