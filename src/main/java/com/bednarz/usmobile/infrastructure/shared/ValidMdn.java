package com.bednarz.usmobile.infrastructure.shared;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^[0-9]{10}$", message = "MDN must be 10 digits long")
public @interface ValidMdn {
    String message() default "Invalid MDN";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}