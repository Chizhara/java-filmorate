package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmUsersLikes;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmStorage extends Storage<Film> {
    FilmUsersLikes addLike(Film film, User user);

    FilmUsersLikes removeLike(Film film, User user);

    List<FilmUsersLikes> getAllFilmLikes();

    FilmUsersLikes getFilmLikes(Film film);
}
