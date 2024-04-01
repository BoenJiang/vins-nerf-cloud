package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.http.RestProject;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.core.valid.RestProjectFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RestProjectValidator implements ConstraintValidator<RestProjectFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtil.isNullOrEmpty(value) ? false : RestProject.contains(value);
    }
}
