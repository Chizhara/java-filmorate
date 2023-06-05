package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotation.LocalDateMinDateConstraint;

import java.time.LocalDate;

@Data
@NotNull
@Builder
public class Film {
    private int id;
    @NotNull
    @NotBlank
    private String name;
    @Length(max = 200)
    private String description;
    @LocalDateMinDateConstraint
    private LocalDate releaseDate;
    @Positive
    private int durationInMinutes;
}
