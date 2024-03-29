package com.example.homeexchange_simpleversion.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = DatesMatchValidator.class)
public @interface DatesMatch {

    String startField();
    String endField();
    String message() default "The date availableTo must be after the date availableFrom.";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
