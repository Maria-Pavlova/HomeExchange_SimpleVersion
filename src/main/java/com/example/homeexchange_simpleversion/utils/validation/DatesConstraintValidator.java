package com.example.homeexchange_simpleversion.utils.validation;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddHomeModel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DatesConstraintValidator implements ConstraintValidator<DatesConstraint, AddHomeModel> {
    @Override
    public void initialize(DatesConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AddHomeModel value, ConstraintValidatorContext context) {
         return  value.getAvailableFrom().isBefore(value.getAvailableTo());
    }
}
