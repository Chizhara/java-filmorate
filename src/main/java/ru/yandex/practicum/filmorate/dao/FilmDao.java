package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmDao {
    Optional<Film> findFilmById(int id);

    List<Film> getAllFilms();

    Integer updateFilm(Film user);

    Integer addFilm(Film user);

    List<Film> getFilmsRating(int count);
}
