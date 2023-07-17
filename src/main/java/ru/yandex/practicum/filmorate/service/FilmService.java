package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmUsersLikes;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class FilmService extends Service<Film> {
    @Autowired
    private UserService userService;
    @Autowired
    private FilmStorage filmStorage;
    public FilmService(JdbcTemplate jdbcTemplate, FilmStorage filmStorage) {
        super(filmStorage);
        System.out.println(jdbcTemplate.queryForList("SELECT * FROM users"));
    }

    public FilmUsersLikes addLike(int filmId, int userId) {
        return filmStorage.addLike(get(filmId), userService.get(userId));
    }

    public FilmUsersLikes removeLike(int filmId, int userId) {
        return filmStorage.removeLike(get(filmId), userService.get(userId));
    }

    public List<Film> getFilmsRating(int count) {
        return filmStorage.getAllFilmLikes().stream()
                .sorted(Comparator.comparingInt(filmLikes -> filmLikes.getFilm().getId()))
                .sorted(Comparator.comparingInt(FilmUsersLikes::getLikesCount).reversed())
                .map(FilmUsersLikes::getFilm)
                .limit(count)
                .collect(Collectors.toList());
    }
}
