package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.FilmUsersLikes;
import ru.yandex.practicum.filmorate.service.FilmService;

@RestController
@RequestMapping("/films/{id}/like")
@Slf4j
public class FilmLikesController {
    @Autowired
    private FilmService filmService;

    @PutMapping("/{userId}")
    public FilmUsersLikes addLike(@PathVariable(value = "id") int filmId, @PathVariable int userId) {
        log.info("Получен запрос Put /films/{}/like {}", filmId, userId);
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{userId}")
    public FilmUsersLikes removeLike(@PathVariable(value = "id") int filmId, @PathVariable int userId) {
        log.info("Получен запрос Delete /films/{}/like {}", filmId, userId);
        try {
            return filmService.removeLike(filmId, userId);
        } catch (NullPointerException e) {
            throw new IllegalAccessError(e.getMessage());
        }
    }
}
