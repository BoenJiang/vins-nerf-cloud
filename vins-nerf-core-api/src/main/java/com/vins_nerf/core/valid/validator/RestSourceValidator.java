package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.http.RestSource;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.core.valid.RestSourceFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RestSourceValidator implements ConstraintValidator<RestSourceFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtil.isNullOrEmpty(value) ? false : RestSource.contains(value);
    }
}
