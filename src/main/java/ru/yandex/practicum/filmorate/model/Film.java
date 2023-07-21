package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotation.LocalDateMinDateConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @Builder.Default
    private List<Genre> genres = new ArrayList<>();
    private MpaRate mpa;

    public void addGenre(Genre genre) {
        genres.add(genre);
    }
}
