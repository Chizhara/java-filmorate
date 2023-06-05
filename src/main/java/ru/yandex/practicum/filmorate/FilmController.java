package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NegativeDurationException;
import ru.yandex.practicum.filmorate.exceptions.OutOfRangeFilmDescriptionException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.GsonBuilder;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/film")
@Slf4j
public class FilmController {
    private static final Set<Film> films = new HashSet<>();
    private static final LocalDate MIN_DATA = LocalDate.of(1985, 11, 28);
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final Gson gson = GsonBuilder.getGson();
    @PostMapping
    public ResponseEntity<Film> addFilm(@RequestBody @Valid Film film) {
        System.out.println(film);
        log.debug("Получен запрос Post /film");
        try {
            validate(film);
            films.add(film);
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (OutOfRangeFilmDescriptionException | DateTimeException ex) {
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        try {
            log.debug("Получен запрос Put /film");
            validate(film);
            Optional<Film> filmOptional = films.stream().filter(film1 -> film1.getId() == film.getId()).findAny();
            if (filmOptional.isPresent()) {
                Film filmTemp = filmOptional.get();
                filmTemp.setDescription(film.getDescription());
                filmTemp.setName(film.getName());
                filmTemp.setDurationInMinutes(film.getDurationInMinutes());
                filmTemp.setReleaseDate(film.getReleaseDate());
            } else {
                films.add(film);
            }
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (DateTimeException | OutOfRangeFilmDescriptionException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }
    }

    @GetMapping
    public Set<Film> getFilms() {
        log.debug("Получен запрос Get /film");
        return films;
    }

    public static void validate(Film film) {
        if(film.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new OutOfRangeFilmDescriptionException("Длина описания фильма слишком длинное",
                    MAX_DESCRIPTION_LENGTH, film.getDescription().length());
        }
        if(film.getReleaseDate().isBefore(MIN_DATA)) {
            throw new DateTimeException("Дата фильма не должна быть меньше допустимой" + ". Минимальная дата: " +
                    MIN_DATA + "; Полученная дата: " + film.getReleaseDate());
        }
    }
}
