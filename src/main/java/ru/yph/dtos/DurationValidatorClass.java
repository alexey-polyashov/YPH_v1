package ru.yph.dtos;

import java.time.Duration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DurationValidatorClass implements ConstraintValidator<DurationValidator, String> {

    @Override
    public void initialize(DurationValidator timeValidator) {

    }

    @Override
    public boolean isValid(String checkValue, ConstraintValidatorContext ctx) {
        try {
            Duration duration = Duration.parse(checkValue);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

}
