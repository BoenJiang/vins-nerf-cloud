package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.RestEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RestEmailValidator.class})
public @interface RestEmailFormat {
    String message() default "email format is illegal.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] whiteList() default {};

    String[] blackList() default {};
}
