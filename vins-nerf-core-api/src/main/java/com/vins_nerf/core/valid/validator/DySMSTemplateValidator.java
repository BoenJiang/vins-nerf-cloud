package com.vins_nerf.core.valid.validator;

import com.vins_nerf.core.valid.DySMSTemplateFormat;
import com.vins_nerf.core.enums.DySmsTemplate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DySMSTemplateValidator implements ConstraintValidator<DySMSTemplateFormat, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return DySmsTemplate.contains(s);
    }
}
