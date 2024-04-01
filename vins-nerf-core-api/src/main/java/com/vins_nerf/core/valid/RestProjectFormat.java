package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.RestProjectValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RestProjectValidator.class})
public @interface RestProjectFormat {
    String message() default "project's name is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
