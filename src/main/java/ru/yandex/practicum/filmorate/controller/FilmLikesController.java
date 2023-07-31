package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NoDataFoundException;
import ru.yandex.practicum.filmorate.service.FilmService;

@RestController
@RequestMapping("/films/{id}/like")
@Slf4j
public class FilmLikesController {
    @Autowired
    private FilmService filmService;

    @PutMapping("/{userId}")
    public void addLike(@PathVariable(value = "id") int filmId, @PathVariable int userId) {
        log.info("Получен запрос Put /films/{}/like {}", filmId, userId);
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{userId}")
    public void removeLike(@PathVariable(value = "id") int filmId, @PathVariable int userId) {
        log.info("Получен запрос Delete /films/{}/like {}", filmId, userId);
        try {
            filmService.removeLike(filmId, userId);
        } catch (NoDataFoundException e) {
            throw new IllegalAccessError(e.getMessage());
        }
    }
}
