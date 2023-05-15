package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.EntityAlreadyExistsException;
import ru.yandex.practicum.javafilmorate.exceptions.EntityDoesNotExistException;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidPathVariableOrParameterException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    public Film addNewFilmToStorage(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilmInStorage(Film film) {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getAllFilmsFromStorage() {
        return filmStorage.getAllFilms();
    }

    public boolean doesFilmExistInStorage(long id) {
        return filmStorage.doesFilmExist(id);
    }

    public Film getFilmFromStorage(long id) {
        checkFilmExistence(id);
        return filmStorage.getFilm(id);
    }

    public void likeFilmInStorage(long id, long userId) {
        checkFilmExistence(id);
        Film film = filmStorage.getFilm(id);
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }

        if (userStorage.getUser(id) == null) {
            throw new EntityDoesNotExistException(
                    String.format("User with id = %d does not exist", userId));
        }

        if (film.getLikes().contains(userId)) {
            throw new EntityAlreadyExistsException(
                    String.format("User with  userId = %d " +
                            "already liked the movie with filmId = %d exist", id, userId));
        }

        film.getLikes().add(userId);
        film.setLikesAmount(film.getLikes().size());
    }

    public void deleteLikeFilmInStorage(long id, long userId) {
        checkFilmExistence(id);
        Film film = filmStorage.getFilm(id);
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }

        if (userStorage.getUser(id) == null) {
            throw new EntityDoesNotExistException(
                    String.format("User with id = %d does not exist", userId));
        }

        if (!film.getLikes().contains(userId)) {
            throw new EntityDoesNotExistException(
                    String.format("User with userId = %d " +
                            "did not like the movie с filmId = %d exist", id, userId));
        }

        film.getLikes().remove(userId);
        film.setLikesAmount(film.getLikes().size());
    }

    public List<Film> getMostLikedFilmsFromStorage(long count) {
        if (filmStorage.getAllFilms().isEmpty()) {
            return filmStorage.getAllFilms();
        }

        return filmStorage.getAllFilms()
                .stream()
                .sorted((o1, o2) -> o2.getLikesAmount() - o1.getLikesAmount())
                .limit(count)
                .collect(Collectors.toList());
    }

    private void checkFilmExistence(long id) {

        if (id < 1) {
            String exceptionMessage = String.format("Film with id = %d cannot exist", id);
            log.warn("Movie request failed. Exception message: {}",
                    exceptionMessage);
            throw new InvalidPathVariableOrParameterException("id", exceptionMessage);
        }

        if (!doesFilmExistInStorage(id)) {
            String exceptionMessage = String.format("Film with  id = %d cannot exist", id);
            log.warn("Movie request failed. Exception message: {}",
                    exceptionMessage);
            throw new EntityDoesNotExistException(exceptionMessage);
        }
    }
}
