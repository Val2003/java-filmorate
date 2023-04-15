package ru.yandex.practicum.filmorate.util;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DurationValidatorTest {

    @Test
    void isDurationNegativeOrZero() {

        var titanicWithNegativeDuration = Film.builder().id(1)
                .name("Titanic")
                .description("Test Description")
                .duration(-90)
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        assertTrue(DurationValidator.isDurationNegativeOrZero(titanicWithNegativeDuration.getDuration()));
    }
}