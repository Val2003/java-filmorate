package ru.yandex.practicum.javafilmorate.model;

import lombok.*;
import ru.yandex.practicum.javafilmorate.util.DurationPositiveOrZero;
import ru.yandex.practicum.javafilmorate.util.FilmDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Film {

    @EqualsAndHashCode.Exclude
    private long id;
    @NotNull
    @NotBlank
    private String name;
    @Size(max = 200, message = "Description length must not exceed 200 characters")
    private String description;
    @FilmDate
    private LocalDate releaseDate;
    @DurationPositiveOrZero
    private Duration duration;
    private Set<Long> likes;
    private int likesAmount;
}
