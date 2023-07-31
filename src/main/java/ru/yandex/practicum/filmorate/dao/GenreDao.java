package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenreDao {

    Optional<Genre> getGenreById(int id);

    List<Genre> getGenres();

    List<Genre> findGenresByFilmId(int genreId);

    void removeFilmGenresByFilmId(int filmId);

    void addGenreToFilmById(int filmId, int genreId);

    Map<Integer, List<Integer>> getRawFilmIdWithGenresIds();
}
