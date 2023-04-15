package ru.yandex.practicum.filmorate.util;

import java.time.LocalDate;

public final class LocalDateValidator {
private LocalDateValidator() {}
    public static boolean isDateTooOld(LocalDate dateToCheck, LocalDate theOldestPossibleDate) {

        return dateToCheck.isBefore(theOldestPossibleDate);
    }

    public static boolean isDateInTheFuture(LocalDate dateToCheck) {

        return dateToCheck.isAfter(LocalDate.now());
    }
}
