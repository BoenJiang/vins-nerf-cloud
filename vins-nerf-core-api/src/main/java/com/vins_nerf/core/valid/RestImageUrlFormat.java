package com.vins_nerf.core.valid;

import com.vins_nerf.core.valid.validator.RestImageUrlValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RestImageUrlValidator.class})
public @interface RestImageUrlFormat {
    String message() default "image url format is illegal.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
