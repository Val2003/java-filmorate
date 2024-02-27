package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.ObjDoesNotExistException;
import ru.yandex.practicum.javafilmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MpaDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Mpa> getAll() {
        String sql = "SELECT id, name " +
                "FROM MPA";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
    }

    public Mpa getMpaById(long id) {
        String sql = "SELECT id, name " +
                "FROM MPA " +
                "WHERE id = ?";

        try {
            Mpa mpa = jdbcTemplate.queryForObject(sql,
                    (ResultSet rs, int rowNum) -> makeMpa(rs),
                    id);
            if (mpa != null) {
                log.info("MPA rating found: with id = {} title = {}", mpa.getId(), mpa.getName());
            }
            return mpa;
        } catch (EmptyResultDataAccessException e) {
            log.debug("MPA rating with id {} not found.", id);
            throw new ObjDoesNotExistException(String.format(
                    "MPA with id %d not found.", id));
        }
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Mpa(id, name);
    }
}
