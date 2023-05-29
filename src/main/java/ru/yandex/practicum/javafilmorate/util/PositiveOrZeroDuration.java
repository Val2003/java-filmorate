package ru.yandex.practicum.javafilmorate.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PositiveOrZeroDirationValidator.class)
@Documented
public @interface PositiveOrZeroDuration {

    String message() default "{Movie duration must be greater than or equal to 0 sec.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
