package ru.yandex.practicum.javafilmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.ObjAlreadyExistsException;
import ru.yandex.practicum.javafilmorate.exceptions.ObjDoesNotExistException;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@Qualifier("filmStorage")
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    private long idToNewFilm = 1;

    private long generateFilmId() {
        long idToSet = idToNewFilm;
        idToNewFilm++;
        return idToSet;
    }

    @Override
    public Film addFilm(Film film) {

        if (films.containsValue(film)) {
            String exceptionMessage = "The movie has already been added to the library";
            log.warn("Error when adding a new movie. Exception message: {}",
                    exceptionMessage);
            throw new ObjAlreadyExistsException(exceptionMessage);
        }

        film.setId(generateFilmId());
        films.put(film.getId(), film);
        return film;

    }

    @Override
    public Film getFilm(Long id) {

        if (!films.containsKey(id)) {
            String exceptionMessage = String.format("Movie with requested id = %d does not exist", id);
            log.warn("Movie request failed. Exception message: {}",
                    exceptionMessage);
            throw new ObjDoesNotExistException(exceptionMessage);
        }
        return films.get(id);
    }

    @Override
    public Film removeFilm(Long id) {

        if (!films.containsKey(id)) {
            String exceptionMessage = String.format("Movie with requested id = %d does not exist", id);
            log.warn("Movie request failed. Exception message: {}",
                    exceptionMessage);
            throw new ObjDoesNotExistException(exceptionMessage);
        }

        return films.remove(id);
    }

    @Override
    public Film updateFilm(Film film) {

        if (!films.containsKey(film.getId())) {
            String exceptionMessage = "Movie to be updated does not exist";
            log.warn("An error occurred while updating an existing movie. Exception message: {}", exceptionMessage);
            throw new ObjDoesNotExistException(exceptionMessage);
        }

        films.remove(film.getId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public boolean doesFilmExist(long id) {
        return films.containsKey(id);
    }

    public List<Film> getMostLikedFilms(int limit) {
        if (this.getAllFilms().isEmpty()) {
            return this.getAllFilms();
        }

        return this.getAllFilms()
                .stream()
                .sorted((o1, o2) -> o2.getRate() - o1.getRate())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
