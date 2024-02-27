package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmGenreDao {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;

    public List<Genre> getFilmGenre(long id) {
        String sql = "SELECT g.id, g.name " +
                "FROM film_genre fg " +
                "LEFT JOIN genre g ON  fg.genre_id = g.id " +
                "WHERE fg.film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> genreDao.makeGenre(rs), id);
    }

    public Film insertFilmGenre(Film film) {
        String sql = "INSERT INTO FILM_GENRE(FILM_ID,GENRE_ID)  " +
                "VALUES(?,?)";

        List<Genre> uniqGenres = film.getGenres().stream().distinct().collect(Collectors.toList());

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, film.getId());
                        ps.setLong(2, uniqGenres.get(i).getId());
                    }

                    public int getBatchSize() {
                        return uniqGenres.size();
                    }
                });
        film.setGenres(uniqGenres);
        return film;
    }

    public void deleteAllFilmGenresByFilmId(long filmId) {

        String sql = "DELETE FROM FILM_GENRE " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sql, filmId);
    }

}
