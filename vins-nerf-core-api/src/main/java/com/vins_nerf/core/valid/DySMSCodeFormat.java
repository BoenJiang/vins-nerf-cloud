package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.DySMSCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DySMSCodeValidator.class})
public @interface DySMSCodeFormat {
    String message() default "smscode is illegal.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}