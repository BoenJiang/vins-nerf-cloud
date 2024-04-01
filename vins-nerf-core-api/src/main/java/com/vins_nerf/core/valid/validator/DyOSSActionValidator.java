package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.valid.DyOSSActionFormat;
import com.vins_nerf.core.enums.DyOSSAction;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DyOSSActionValidator implements ConstraintValidator<DyOSSActionFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return DyOSSAction.contains(value);
    }
}
