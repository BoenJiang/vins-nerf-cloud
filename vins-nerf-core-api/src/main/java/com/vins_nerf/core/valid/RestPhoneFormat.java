package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.RestPhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RestPhoneValidator.class})
public @interface RestPhoneFormat {
    String message() default "phone format is illegal.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] whiteList() default {};

    String[] blackList() default {};
}

