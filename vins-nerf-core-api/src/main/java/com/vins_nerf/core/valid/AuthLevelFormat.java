package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.AuthLevelValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AuthLevelValidator.class})
public @interface AuthLevelFormat {
    String message() default "auth-level's name is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
