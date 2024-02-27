package ru.yandex.practicum.javafilmorate.util;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = FilmDateValidator.class)
@Documented
public @interface FilmDate {

    String message() default "{The release date is too old. You can add films with a release date after 28.12.1895}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

