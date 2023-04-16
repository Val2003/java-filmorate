package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.EntityAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.EntityDoesNotExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.DurationValidator;
import ru.yandex.practicum.filmorate.util.LocalDateValidator;
import ru.yandex.practicum.filmorate.util.StringValidator;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final LocalDate THE_OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int idToNewFilm = 1;

    private int generateFilmId() {
        return idToNewFilm++;
    }

    @PostMapping
    public Film addNewFilm(@RequestBody @Valid Film film) {
        log.info("Add request received POST /films");
        if (validateFilm(film)) {
            if (films.containsValue(film)) {
                String exceptionMessage = "The movie has already been added to the library";
                log.warn("Error in add film. Exception message: {}",
                        exceptionMessage);
                throw new EntityAlreadyExistsException(exceptionMessage);
            }

            film.setId(generateFilmId());
            films.put(film.getId(), film);
            return film;

        }

        return null;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        if (validateFilm(film)) {

            if (!films.containsKey(film.getId())) {
                String exceptionMessage = "Updating movie not found";
                log.warn("Error in film update. Exception message: {}", exceptionMessage);
                throw new EntityDoesNotExistException(exceptionMessage);
            }

            films.remove(film.getId(), film);
            films.put(film.getId(), film);
            return film;
        }

        return null;
    }

    @GetMapping
     public List<Film> getAll() {

        return new ArrayList<>(films.values());
    }

    private boolean validateFilm(Film film) {
        boolean valid = false;
        if (StringValidator.isNullOrEmpty(film.getName())) {
            String exceptionMessage = "Movie title must not be empty";
            log.warn("Error validation. Exception message: {}", exceptionMessage);
            throw new FilmValidationException(exceptionMessage);
        } else if (StringValidator.isLengthBiggerThanMaxLength(film.getDescription(), MAX_DESCRIPTION_LENGTH)) {
            String exceptionMessage = "Maximum description length - " + MAX_DESCRIPTION_LENGTH + " characters";
            log.warn("Error validation. Exception message: {}", exceptionMessage);
            throw new FilmValidationException(exceptionMessage);
        } else if (LocalDateValidator.isDateTooOld(film.getReleaseDate(), THE_OLDEST_RELEASE_DATE)) {
            String exceptionMessage = "Too old data of release."
                    + " add film after 28.12.1895";
            log.warn("Error validation. Exception message: {}", exceptionMessage);
            throw new FilmValidationException(exceptionMessage);
        } else if (DurationValidator.isDurationNegativeOrZero(film.getDuration())) {
            String exceptionMessage = "Movie duration must be positive";
            log.warn("Error validation. Exception message: {}", exceptionMessage);
            throw new FilmValidationException(exceptionMessage);
        } else {valid=true;
        }
        return valid;
    }
}
