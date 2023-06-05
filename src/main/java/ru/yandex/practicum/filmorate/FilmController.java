package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/film")
@Slf4j
public class FilmController {

    private static final Set<Film> films = new HashSet<>();

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        log.debug("Получен запрос Post /film");
        films.add(film);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        log.debug("Получен запрос Put /film");
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
    }

    @GetMapping
    public Set<Film> getFilms() {
        log.debug("Получен запрос Get /film");
        return films;
    }
}
