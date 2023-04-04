package com.example.homeexchange_simpleversion.validation;

import com.example.homeexchange_simpleversion.validation.DatesMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import java.time.LocalDate;

public class DatesMatchValidator implements ConstraintValidator<DatesMatch, Object> {

    private String startField;
    private String endField;
    private String message;
    @Override
    public void initialize(DatesMatch constraintAnnotation) {
        this.startField = constraintAnnotation.startField();
        this.endField = constraintAnnotation.endField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        LocalDate startFieldValue =(LocalDate) beanWrapper.getPropertyValue(this.startField);
        LocalDate endFieldValue = (LocalDate) beanWrapper.getPropertyValue(this.endField);

        boolean valid;

        if (startFieldValue == null) {
           valid = endFieldValue == null;
        }else {
            assert endFieldValue != null;
            valid = endFieldValue.isAfter(startFieldValue);
        }
        if (!valid){
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(endField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
