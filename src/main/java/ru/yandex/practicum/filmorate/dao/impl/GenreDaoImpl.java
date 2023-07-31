package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Genre> findGenresByFilmId(int filmId) {
        log.debug("Вызов метода findGenresByFilmId класса GenreDaoImpl filmId = {}", filmId);
        String sql = "SELECT g.genre_id, g.genre_name FROM film_genres AS fg " +
                "INNER JOIN genres AS g ON g.genre_id = fg.genre_id WHERE fg.film_id = :film_id";
        return jdbcOperations.query(sql, Map.of("film_id", filmId), (rs, rowNum) -> ModelsParser.parseGenre(rs));
    }

    @Override
    public Optional<Genre> getGenreById(int genreId) {
        log.debug("Вызов метода getGenreById класса GenreDaoImpl id = {}", genreId);
        String sql = "SELECT * FROM genres WHERE genre_id = :genre_id";
        List<Genre> genres = jdbcOperations.query(sql, Map.of("genre_id", genreId),
                (rs, rowNum) -> ModelsParser.parseGenre(rs));
        if (genres.size() != 1) {
            return Optional.empty();
        }

        return Optional.of(genres.get(0));
    }

    @Override
    public List<Genre> getGenres() {
        log.debug("Вызов метода getGenres класса GenreDaoImpl");
        String sql = "SELECT * FROM genres";
        return jdbcOperations.query(sql, (rs, rowNum) -> ModelsParser.parseGenre(rs));
    }

    @Override
    public void removeFilmGenresByFilmId(int filmId) {
        log.debug("Вызов метода getGenres класса GenreDaoImpl");
        String sql = "DELETE FROM film_genres WHERE film_id = :film_id";
        jdbcOperations.update(sql, Map.of("film_id", filmId));
    }

    @Override
    public void addGenreToFilmById(int filmId, int genreId) {
        try {
            log.debug("Вызов метода findGenresByFilmId класса GenreDaoImpl filmId = {}", filmId);
            String sql = "INSERT INTO film_genres(film_id, genre_id) VALUES(:film_id, :genre_id)";
            jdbcOperations.update(sql, Map.of("film_id", filmId, "genre_id", genreId));
        } catch (DuplicateKeyException ignored) {
        }
    }

    //Метод возвращает Map'у, где ключом является идентификатор фильма, а значениями - идентификаторы жанров
    @Override
    public Map<Integer, List<Integer>> getRawFilmIdWithGenresIds() {
        Map<Integer, List<Integer>> filmIdToGenresIds = new HashMap<>();
        String sql = "SELECT * FROM film_genres";
        jdbcOperations.query(sql, (rs, rowNum) -> {
            int filmId = rs.getInt("film_id");
            int genreId = rs.getInt("genre_id");
            if (!filmIdToGenresIds.containsKey(filmId)) {
                filmIdToGenresIds.put(filmId, new ArrayList<>());
            }
            filmIdToGenresIds.get(filmId).add(genreId);
            return null;
        });
        return filmIdToGenresIds;
    }
}
