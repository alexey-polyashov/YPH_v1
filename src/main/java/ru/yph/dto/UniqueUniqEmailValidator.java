package ru.yph.dto;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yph.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUniqEmailValidator implements ConstraintValidator<UniqUserEmail, String> {


    @Autowired
    private UserService usersService;

    @Override
    public void initialize(UniqUserEmail uniqUserEmail) {

    }

    @Override
    public boolean isValid(String checkValue, ConstraintValidatorContext ctx) {
        if (usersService.findByEmail(checkValue).isPresent()){
            return false;
        }
        return true;
    }

}
