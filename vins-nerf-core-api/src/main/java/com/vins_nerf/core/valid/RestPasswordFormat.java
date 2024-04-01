package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.RestPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RestPasswordValidator.class})
public @interface RestPasswordFormat {
    String message() default "Password is illegal.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
