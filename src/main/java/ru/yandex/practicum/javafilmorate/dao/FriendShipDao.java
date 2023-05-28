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
public class FriendShipDao {

    private final JdbcTemplate jdbcTemplate;

    public void addFriends(long friend1, long friend2) {
        String sql = "MERGE INTO FRIENDSHIP f USING (VALUES (?,?)) S(friend1, friend2)\n" +
                "ON f.FRIEND1_ID = S.friend1 AND f.FRIEND2_ID = S.friend2 \n" +
                "WHEN NOT MATCHED THEN INSERT VALUES ( S.friend1, S.friend2)";
        try {
            jdbcTemplate.update(sql, friend1, friend2);
        } catch (DataIntegrityViolationException e) {
            log.debug("User with id {} or {} not found.", friend1, friend2);
            throw new ObjDoesNotExistException(
                    String.format("User with id %d or %d not found.", friend1, friend2));
        }
    }

    public void deleteFriends(long friend1, long friend2) {
        String sql = "MERGE INTO FRIENDSHIP f USING (VALUES (?,?)) S(friend1, friend2)\n" +
                "ON f.FRIEND1_ID = S.friend1 AND f.FRIEND2_ID = S.friend2 \n" +
                "WHEN  MATCHED THEN DELETE";

        jdbcTemplate.update(sql, friend1, friend2);
    }

    public List<User> getUserFriends(long id) {
        String sql = "SELECT  UF.id, UF.email, UF.login, UF.name, UF.birthday " +
                "FROM FRIENDSHIP f " +
                "LEFT JOIN USER_FILMORATE UF on f.FRIEND2_ID = UF.ID " +
                "WHERE FRIEND1_ID = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> User.makeUser(rs), id);
    }

    public List<User> getCommonFriends(long friend1, long friend2) {

        String sql2 = "SELECT UF.ID, UF.EMAIL, UF.LOGIN, UF.NAME, UF.BIRTHDAY \n" +
                "FROM FRIENDSHIP F\n" +
                "INNER JOIN USER_FILMORATE UF ON F.FRIEND2_ID = UF.ID\n" +
                "WHERE FRIEND1_ID = ? AND FRIEND2_ID IN (\n" +
                "        SELECT FRIEND2_ID\n" +
                "        FROM FRIENDSHIP\n" +
                "        WHERE FRIEND1_ID = ?\n" +
                "     )";

        return jdbcTemplate.query(sql2, (rs, rowNum) -> User.makeUser(rs), friend1, friend2);
    }

}
