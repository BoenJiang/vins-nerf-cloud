package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.core.valid.AuthLevelFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuthLevelValidator implements ConstraintValidator<AuthLevelFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtil.isNullOrEmpty(value) ? false : AuthLevel.contains(value);
    }
}
