package com.exadel.discountwebapp.discount.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FlatAmountOrPercentageFieldsNotNullValidator.class})
public @interface FlatAmountOrPercentageFieldsNotNull {
    String message() default "Flat amount or percentage should not be null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}