package ru.yph.dto;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yph.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUniqUserValidator implements ConstraintValidator<UniqUserName, String> {


    @Autowired
    private UserService usersService;

    @Override
    public void initialize(UniqUserName uniqUserName) {

    }

    @Override
    public boolean isValid(String checkValue, ConstraintValidatorContext ctx) {
        if (usersService.findByLogin(checkValue).isPresent()){
            return false;
        }
        return true;
    }

}
