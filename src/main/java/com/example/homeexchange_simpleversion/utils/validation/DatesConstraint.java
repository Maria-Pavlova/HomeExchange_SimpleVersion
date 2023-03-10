package com.example.homeexchange_simpleversion.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DatesConstraintValidator.class)
public @interface DatesConstraint {

    String message() default "The second date must be after the first.";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
