package ru.yph.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DurationValidatorClass.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DurationValidator {

    String message() default "Не правильный формат длительности";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
