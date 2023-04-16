package ru.yandex.practicum.filmorate.util;

public final class DurationValidator {
private DurationValidator() {
}

    public static boolean isDurationNegativeOrZero(Integer duration) {

        return (duration <= 0);
    }

}
