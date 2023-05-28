package ru.yandex.practicum.javafilmorate.model;

import lombok.*;
import ru.yandex.practicum.javafilmorate.util.StringWithoutSpaceSymbolValidator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class User {
    @EqualsAndHashCode.Exclude
    private long id;
    @NotNull(message = "Email cannot be left blank")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be correct")
    private String email;
    @NotNull(message = "Login cannot be set")
    @NotBlank(message = "Login cannot be empty")
    @StringWithoutSpaceSymbolValidator
    private String login;
    private String name;
    @Past(message = "Birthday can't be in the future")
    private LocalDate birthday;
    private List<User> friends;

    public static User makeUser(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        return User.builder()
                .id(id)
                .email(email)
                .login(login)
                .name(name)
                .birthday(birthday)
                .build();
    }
}
