package ru.yandex.practicum.javafilmorate.model;

import lombok.*;
import ru.yandex.practicum.javafilmorate.util.FilmDate;
import ru.yandex.practicum.javafilmorate.util.PositiveOrZeroDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Film implements Serializable {

    @EqualsAndHashCode.Exclude
    private long id;
    @NotNull
    @NotBlank
    private String name;
    @Size(max = 200, message = "Description length must not exceed 200 characters")
    private String description;
    @FilmDate
    private LocalDate releaseDate;
    @PositiveOrZeroDuration
    private Integer duration;
    private List<User> likes;
    private int rate;
    private Mpa mpa;

    private List<Genre> genres;

    private int likesAmount;

    public Film(long id,
                String name,
                String description,
                LocalDate releaseDate,
                Integer duration,
                List<User> likes,
                int rate,
                Mpa mpa,
                List<Genre> genres,
                int likesAmount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = likes;
        this.rate = rate;
        this.mpa = mpa;
        this.genres = genres;
        this.likesAmount = likesAmount;
    }
}
