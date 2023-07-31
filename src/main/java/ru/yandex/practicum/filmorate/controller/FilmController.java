package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NoDataFoundException;
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
        return filmService.add(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос Put /film  {}", film.toString());
        try {
            return filmService.updateFilmById(film);
        } catch (NoDataFoundException e) {
            throw new IllegalAccessError(e.getMessage());
        }
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос Get /film");
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable int id) {
        log.info("Получен запрос Get /user");
        try {
            return filmService.findFilmById(id);
        } catch (NoDataFoundException e) {
            throw new IllegalAccessError(e.getMessage());
        }
    }

    @GetMapping("/popular")
    public List<Film> getFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получен запрос Get /film/popular");
        return filmService.getFilmsRating(count);
    }
}
