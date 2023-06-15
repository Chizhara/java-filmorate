package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public class FilmService extends Service<Film> {

    public void add(Film film) {
        this.add(film);
    }

    public boolean updateFilm(Film film) {
        return update(film);
    }

    public List<Film> getFilms() {
        return getAll();
    }
}
