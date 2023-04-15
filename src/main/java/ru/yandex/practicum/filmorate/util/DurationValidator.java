package ru.yandex.practicum.filmorate.util;

import java.time.Duration;

public final class DurationValidator {
private DurationValidator() {
}
    public static boolean isDurationNegativeOrZero(Duration duration) {

        return duration.toSeconds() <= 0;
    }

}
