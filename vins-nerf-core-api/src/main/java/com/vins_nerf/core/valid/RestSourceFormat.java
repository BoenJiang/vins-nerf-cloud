package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.RestSourceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RestSourceValidator.class})
public @interface RestSourceFormat {
    String message() default "source's name is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
