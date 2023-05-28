package ru.yandex.practicum.javafilmorate.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class PositiveOrZeroDirationValidator implements ConstraintValidator<PositiveOrZeroDuration, Duration> {
    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext constraintValidatorContext) {
        return duration.toSeconds() >= 0;
    }
}
