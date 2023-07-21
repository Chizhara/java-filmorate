package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.NoDataFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
public class GenreController {

    @Autowired
    GenreService genreService;

    @GetMapping("/{genreId}")
    public Genre getGenreById(@PathVariable int genreId) {
        log.info("Получен запрос Get /genres/{}  ", genreId);
        try {
            return genreService.getMpaById(genreId);
        } catch (NoDataFoundException e) {
            throw new IllegalAccessError(e.getMessage());
        }
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
}
