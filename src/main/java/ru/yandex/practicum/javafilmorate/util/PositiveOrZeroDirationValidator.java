package ru.yandex.practicum.javafilmorate.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PositiveOrZeroDirationValidator implements ConstraintValidator<PositiveOrZeroDuration, Integer> {
    @Override
    public boolean isValid(Integer duration, ConstraintValidatorContext constraintValidatorContext) {
        return duration >= 0;
    }
}
