package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.DySMSTemplateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DySMSTemplateValidator.class})
public @interface DySMSTemplateFormat {
    String message() default "sms-template's name is illegal.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
