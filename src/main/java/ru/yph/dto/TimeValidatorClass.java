package ru.yph.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeValidatorClass implements ConstraintValidator<TimeValidator, String> {

    @Override
    public void initialize(TimeValidator timeValidator) {

    }

    @Override
    public boolean isValid(String checkValue, ConstraintValidatorContext ctx) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setLenient(false);
        try {
            format.parse(checkValue);
            return true;
        }catch (ParseException e) {
            return false;
        }
    }

}
