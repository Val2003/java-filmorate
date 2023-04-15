package ru.yandex.practicum.filmorate.util;

public  final class StringValidator {
    private StringValidator(){}

    public static boolean isLengthBiggerThanMaxLength(String stringToValidate, int maxLength) {

        return stringToValidate.length() > maxLength;
    }

    public static boolean isNullOrEmpty(String stringToValidate) {

        return stringToValidate == null || stringToValidate.isEmpty();
    }

    public static boolean isEmptyOrContainsSpaceSymbol(String stringToValidate) {

        return stringToValidate.isEmpty() || stringToValidate.contains(" ");
    }

}
