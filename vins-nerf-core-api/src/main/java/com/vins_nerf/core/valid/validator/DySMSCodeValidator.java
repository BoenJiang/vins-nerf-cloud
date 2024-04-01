package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.core.valid.DySMSCodeFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DySMSCodeValidator implements ConstraintValidator<DySMSCodeFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtil.isSMSCode(value);
    }
}
