package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.ObjDoesNotExistException;
import ru.yandex.practicum.javafilmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Genre> getAll() {
        String sql = "SELECT id, name " +
                "FROM Genre";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    public Genre getGenreById(long id) {
        String sql = "SELECT id, name " +
                "FROM Genre " +
                "WHERE id = ?";

        try {
            Genre mpa = jdbcTemplate.queryForObject(sql,
                    (ResultSet rs, int rowNum) -> makeGenre(rs),
                    id);
            if (mpa != null) {
                log.info("Found Genre: with id = {} title = {}", mpa.getId(), mpa.getName());
            }
            return mpa;
        } catch (EmptyResultDataAccessException e) {
            log.debug("Genre with id {} not found.", id);
            throw new ObjDoesNotExistException(String.format(
                    "Genre with id %d not found.", id));
        }
    }

    public Genre makeGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }
}
