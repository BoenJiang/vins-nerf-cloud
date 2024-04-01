package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.valid.RestGenderFormat;
import com.vins_nerf.core.enums.SysGender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RestGenderValidator implements ConstraintValidator<RestGenderFormat, Integer> {
    private boolean canBeNullOrEmpty;

    @Override
    public void initialize(RestGenderFormat restGenderFormat) {
        this.canBeNullOrEmpty = restGenderFormat.canBeNullOrEmpty();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return canBeNullOrEmpty || SysGender.getByState(value) != SysGender.EMPTY;
    }
}
