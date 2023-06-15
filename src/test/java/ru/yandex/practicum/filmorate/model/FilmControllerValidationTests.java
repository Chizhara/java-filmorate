package ru.yandex.practicum.filmorate.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmControllerValidationTests {

    @Autowired
    Validator validator;

    @Test
    public void validateCorrectFilm() {
        Film film = Film.builder().id(1).name("Фильм").duration(5)
                .releaseDate(LocalDate.of(2000, 1, 1)).description("Описание фильма").build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldNotValidateFilmWithDate1895dec27() {
        Film film = Film.builder().id(1).name("Фильм").releaseDate(LocalDate.of(1895, 12, 27))
                        .description("Описание фильма").duration(5).build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldNotValidateFilmWithNullName() {
        Film film = Film.builder().id(1).name(null).duration(5)
                .releaseDate(LocalDate.of(2000, 12, 27)).description("Описание фильма").build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldNotValidateFilmWithDescriptionLength201() {
        Film film = Film.builder().id(1).name("Фильм").duration(5)
                .releaseDate(LocalDate.of(2000, 12, 27))
                .description("О".repeat(201)).build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldNotValidateFilmWithNegativeDuration() {
        Film film = Film.builder().id(1).name("Фильм").duration(0)
                .releaseDate(LocalDate.of(2000, 12, 27)).description("Описание фильма").build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }
}
