package ru.yph.dtos;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeValidatorClass.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeValidator {

    String message() default "Время не соответствует шаблону HH:MM";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
