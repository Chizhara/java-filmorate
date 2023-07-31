package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exceptions.NoDataFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final FilmDao filmDao;
    private final LikeDao likeDao;
    private final GenreDao genreDao;
    private final UserDao userDao;

    public Film updateFilmById(Film film) {
        log.debug("Вызов метода updateFilmById класса FilmService Film = {}", film);
        Integer updatedFilmId = filmDao.updateFilm(film);
        if (updatedFilmId == null) {
            throw new NoDataFoundException("Отсутствует фильм с значением поля id = " + film.getId());
        }
        genreDao.removeFilmGenresByFilmId(updatedFilmId);
        addGenresToFilmInDB(film);
        return findFilmById(updatedFilmId);
    }

    public List<Film> getAllFilms() {
        log.debug("Вызов метода getAllFilms класса FilmService");
        List<Film> films = filmDao.getAllFilms();
        addGenresToFilms(films);
        return films;
    }

    public Film findFilmById(int id) {
        log.debug("Вызов метода findFilmById класса FilmService id = {}", id);
        Optional<Film> filmOptional = filmDao.findFilmById(id);
        if (filmOptional.isEmpty()) {
            throw new NoDataFoundException("Отсутствует фильм с значением поля id =  " + id);
        }
        Film film = filmOptional.get();
        film.setGenres(genreDao.findGenresByFilmId(film.getId()));
        return film;
    }

    public Film add(Film film) {
        log.debug("Вызов метода add класса FilmService film = {}", film);
        Integer filmId = filmDao.addFilm(film);
        film.setId(filmId);
        addGenresToFilmInDB(film);
        return findFilmById(filmId);
    }

    public void addLike(int filmId, int userId) {
        log.debug("Вызов метода addLike класса FilmService filmId = {}, userId = {}", filmId, userId);
        likeDao.addLike(userId, filmId);
    }

    public void removeLike(int filmId, int userId) {
        log.debug("Вызов метода removeLike класса FilmService filmId = {}, userId = {}", filmId, userId);
        if (filmDao.findFilmById(filmId).isEmpty()) {
            throw new NoDataFoundException("Неверные идентификатор фильма filmId = " + filmId);
        }
        if (userDao.findUserById(userId).isEmpty()) {
            throw new NoDataFoundException("Неверные идентификатор пользователя: userId = " + userId);
        }
        likeDao.removeLike(userId, filmId);
    }

    public List<Film> getFilmsRating(int count) {
        log.debug("Вызов метода getFilmsRating класса FilmService count = {}", count);
        return filmDao.getFilmsRating(count);
    }

    //Метод проходит по всем фильмам, передаваемых внешне и выбирает для них идентификаторы фильмов из Map'ы, хранящей
    //идентификаторы фильмов в связке с соответствующими им идентификаторами жанров. После находит конкретные экземпляры
    //жанров и вставляет их значения в конкретный фильм
    private void addGenresToFilms(List<Film> films) {
        Map<Integer, List<Integer>> rawFilmsIdGenresId = genreDao.getRawFilmIdWithGenresIds();
        Map<Integer, Genre> allGenres = genreDao.getGenres().stream()
                .collect(Collectors.toMap(Genre::getId, genre -> genre));

        for (Film film : films) {
            List<Integer> rawFilmGenresIds = rawFilmsIdGenresId.get(film.getId());
            if (rawFilmGenresIds != null) {
                rawFilmGenresIds.forEach(genreId -> film.addGenre(allGenres.get(genreId)));
            }
        }
    }

    private void addGenresToFilmInDB(Film film) {
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genreDao.addGenreToFilmById(film.getId(), genre.getId());
            }
        }
    }
}
