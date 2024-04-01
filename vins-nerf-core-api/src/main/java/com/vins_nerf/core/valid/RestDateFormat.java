package com.vins_nerf.core.valid;

import com.vins_nerf.core.utils.DateUtil;
import com.vins_nerf.core.valid.validator.RestDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RestDateValidator.class})
public @interface RestDateFormat {
    String message() default "Birthday's format is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean canBeNullOrEmpty() default true;

    String dateFormat() default DateUtil.DEFAULT_DATE_FORMAT;
}
