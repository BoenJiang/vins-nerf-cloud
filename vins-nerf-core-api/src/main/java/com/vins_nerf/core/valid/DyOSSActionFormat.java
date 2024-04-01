package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.DyOSSActionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DyOSSActionValidator.class})
public @interface DyOSSActionFormat {
    String message() default "oss-action is illegal.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
