package ru.yandex.practicum.javafilmorate.model;

import lombok.*;
import ru.yandex.practicum.javafilmorate.util.StringWithoutSpaceSymbol;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

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
    @StringWithoutSpaceSymbol
    private String login;
    private String name;
    @Past(message = "Birthday can't be in the future")
    private LocalDate birthday;
    private Set<Long> friends;
}
