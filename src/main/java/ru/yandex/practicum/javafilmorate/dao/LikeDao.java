package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.ObjDoesNotExistException;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeDao {

    private final UserDbStorageDao userDbStorageDao;

    private final JdbcTemplate jdbcTemplate;

    public List<User> getFilmLikes(long id) {
        String sql = "SELECT  UF.id, UF.email, UF.login, UF.name, UF.birthday " +
                "FROM likes l " +
                "LEFT JOIN USER_FILMORATE UF on l.USER_ID = UF.ID " +
                "WHERE film_id = ? ";

        return jdbcTemplate.query(sql, (rs, rowNum) -> userDbStorageDao.makeUser(rs), id);
    }

    public void putLike(long filmId, long userId) {
        String sql = "INSERT INTO LIKES (FILM_ID, USER_ID) " +
                "VALUES (?,?)";
        try {
            jdbcTemplate.update(sql, filmId, userId);
        } catch (DataIntegrityViolationException e) {
            log.debug("Movie with id = {} or User with id = {} not found.", filmId, userId);
            throw new ObjDoesNotExistException(
                    String.format("Movie with id = %d or User with id = %d not found.",
                            filmId, userId));
        }

        String sql2 = "UPDATE FILM\n" +
                "SET LIKES_AMOUNT = LIKES_AMOUNT + 1\n" +
                "WHERE ID = ?";
        jdbcTemplate.update(sql2, filmId);
    }

    public void deleteLike(long filmId, long userId) {
        if (userId < 0) {
            log.debug("User with negative id {} cannot exist.", userId);
            throw new ObjDoesNotExistException(
                    String.format("User with negative id %d cannot exist.", userId));
        }

        String sql = "DELETE FROM LIKES  " +
                "WHERE film_Id = ? AND user_Id = ?";
        try {
            jdbcTemplate.update(sql, filmId, userId);
        } catch (DataIntegrityViolationException e) {
            log.debug("Movie with id = {} or User with id = {} not found.", filmId, userId);
            throw new ObjDoesNotExistException(
                    String.format("Movie with id = %d or User with id = %d not found.",
                            filmId, userId));
        }


        String sql2 = "UPDATE FILM\n" +
                "SET LIKES_AMOUNT = LIKES_AMOUNT - 1\n" +
                "WHERE ID = ?";
        jdbcTemplate.update(sql2, filmId);
    }
}
