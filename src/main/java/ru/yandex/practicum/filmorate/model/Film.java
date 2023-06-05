package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotation.LocalDateMinDateConstraint;

import java.time.LocalDate;

@Data
@Builder
public class Film implements Identifiable {
    private int id;
    @NotBlank
    private String name;
    @Length(max = 200)
    private String description;
    @LocalDateMinDateConstraint(y = 1895, month = 12, d = 28)
    private LocalDate releaseDate;
    @Positive
    private int duration;
}
