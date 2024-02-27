package ru.yandex.practicum.javafilmorate.util;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StringValidatorTest {


    @Test
    void isNullOrEmpty() {
        Film terminatorWithEmptyName = Film.builder().id(1)
                .name("")
                .description("Test description")
                .duration(90)
                .releaseDate(LocalDate.of(1984, 10, 26))
                .build();

        Film terminatorWithNullName = Film.builder().id(1)
                .name("")
                .description("Test description")
                .duration(90)
                .releaseDate(LocalDate.of(1984, 10, 26))
                .build();

        assertTrue(StringValidator.isNullOrEmpty(terminatorWithEmptyName.getName()));
        assertTrue(StringValidator.isNullOrEmpty(terminatorWithNullName.getName()));
    }

}