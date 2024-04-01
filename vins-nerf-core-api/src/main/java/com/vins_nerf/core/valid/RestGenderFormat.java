package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.RestGenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RestGenderValidator.class})
public @interface RestGenderFormat {
    String message() default "Gender's format is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean canBeNullOrEmpty() default true;
}
