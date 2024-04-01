package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.utils.DateUtil;
import com.vins_nerf.core.valid.RestDateFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RestDateValidator implements ConstraintValidator<RestDateFormat, String> {
    private boolean canBeNullOrEmpty;
    private String dateFormat;

    @Override
    public void initialize(RestDateFormat restDateFormat) {
        this.canBeNullOrEmpty = restDateFormat.canBeNullOrEmpty();
        this.dateFormat = restDateFormat.dateFormat();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return canBeNullOrEmpty || DateUtil.isDateFormat(value, this.dateFormat);
    }
}
