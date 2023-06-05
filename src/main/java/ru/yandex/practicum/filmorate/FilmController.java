package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.Service;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Service<Film> filmService = new Service<>();

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
            log.info("Ошибка! передан неизвестный фильм");
            throw new IllegalAccessError();
        }
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос Get /film");
        return filmService.getAll();
    }
}
