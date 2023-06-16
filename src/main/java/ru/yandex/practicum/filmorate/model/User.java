package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.NotContainsBlank;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
public class User implements Identifiable {
    private int id;
    @Email
    private String email;
    @NotNull
    @NotEmpty
    @NotContainsBlank
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
}
