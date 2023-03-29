package com.example.homeexchange_simpleversion.utils.validation;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddHomeModel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;

public class DatesMatchValidator implements ConstraintValidator<DatesMatch, AddHomeModel> {

    private String startField;
    private String endField;
    @Override
    public void initialize(DatesMatch constraintAnnotation) {
        this.startField = constraintAnnotation.startField();
        this.endField = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(AddHomeModel value, ConstraintValidatorContext context) {
       LocalDate startFieldValue = (LocalDate) new BeanWrapperImpl(value)
                .getPropertyValue(startField);
        LocalDate endFieldValue = (LocalDate) new BeanWrapperImpl(value)
                .getPropertyValue(endField);

        if (startFieldValue == null || endFieldValue == null) {
            return true;
        }

        return endFieldValue.isAfter(startFieldValue);
    }
}
