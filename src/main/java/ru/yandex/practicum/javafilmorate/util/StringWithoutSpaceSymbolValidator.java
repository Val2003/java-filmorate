package ru.yandex.practicum.javafilmorate.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringValidator.class)
@Documented
public @interface StringWithoutSpaceSymbolValidator {

    String message() default "{This string field cannot contain spaces}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
