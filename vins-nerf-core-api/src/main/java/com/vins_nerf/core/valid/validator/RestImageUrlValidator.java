package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.core.valid.RestImageUrlFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RestImageUrlValidator implements ConstraintValidator<RestImageUrlFormat, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //TODO
        return StringUtil.isNullOrEmpty(s);
    }
}
