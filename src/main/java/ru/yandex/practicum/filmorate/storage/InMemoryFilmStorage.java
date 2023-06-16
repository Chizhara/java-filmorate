package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmUsersLikes;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage extends InMemoryStorage<Film> implements FilmStorage {

    protected final Map<Integer, FilmUsersLikes> filmLikes = new HashMap<>();

    @Override
    public Film add(Film film) {
        super.add(film);
        filmLikes.put(film.getId(), new FilmUsersLikes(film));
        return film;
    }

    @Override
    public Film update(Film film) {
        super.update(film);
        filmLikes.get(film.getId()).setFilm(film);
        return film;
    }

    @Override
    public FilmUsersLikes addLike(Film film, User user) {
        FilmUsersLikes filmUsersLikes = getFilmLikes(film);
        filmUsersLikes.addUser(user);
        return filmUsersLikes;
    }

    @Override
    public FilmUsersLikes removeLike(Film film, User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        FilmUsersLikes filmUsersLikes = getFilmLikes(film);
        filmUsersLikes.removeUser(user);
        return filmUsersLikes;
    }

    @Override
    public List<FilmUsersLikes> getAllFilmLikes() {
        return new ArrayList<>(filmLikes.values());
    }

    @Override
    public FilmUsersLikes getFilmLikes(Film film) {
        return filmLikes.get(film.getId());
    }
}
