package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    @Autowired
    private FilmService filmService;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос Post /film  {}", film.toString());
        filmService.add(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос Put /film  {}", film.toString());
        if (filmService.update(film)) {
            return film;
        } else {
            throw new IllegalAccessError("Отсутствует фильм с значением поля id = " + film.getId());
        }
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос Get /film");
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable int id) {
        log.info("Получен запрос Get /user");
        try {
            return filmService.get(id);
        } catch (NullPointerException e) {
            throw new IllegalAccessError(e.getMessage());
        }
    }

    @GetMapping("/popular")
    public List<Film> getFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получен запрос Get /film/popular");
        return filmService.getFilmsRating(count);
    }
}
