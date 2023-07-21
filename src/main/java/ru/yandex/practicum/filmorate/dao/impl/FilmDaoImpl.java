package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class FilmDaoImpl implements FilmDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Film> findFilmById(int id) {
        log.debug("Вызов метода findFilmById класса FilmDaoImpl id = {}", id);
        String sql =
                "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration_in_minutes, " +
                        "mr.mpa_rate_id, mr.mpa_rate_name FROM films AS f " +
                        "LEFT JOIN mpa_rates AS mr ON mr.mpa_rate_id = f.mpa_rate_id " +
                        "WHERE f.film_id = :film_id";

        List<Film> filmList = jdbcOperations.query(sql,
                Map.of("film_id", id), ((rs, rowNum) -> ModelsParser.parseFilm(rs)));
        if (filmList.size() != 1) {
            return Optional.empty();
        }

        return Optional.of(filmList.get(0));
    }

    @Override
    public List<Film> getAllFilms() {
        log.debug("Вызов метода getAllFilms класса FilmDaoImpl");
        String sql =
                "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration_in_minutes, " +
                        "mr.mpa_rate_id, mr.mpa_rate_name FROM films AS f " +
                        "LEFT JOIN mpa_rates AS mr ON mr.mpa_rate_id = f.mpa_rate_id;";

        return jdbcOperations.query(sql, (rs, rowNum) -> ModelsParser.parseFilm(rs));
    }

    @Override
    public Integer updateFilm(Film film) {
        log.debug("Вызов метода updateFilm класса FilmDaoImpl");
        String sql =
                "UPDATE films " +
                        "SET film_name = :film_name, description = :description, release_date = :release_date, " +
                        "duration_in_minutes = :duration_in_minutes, mpa_rate_id = :mpa_rate_id " +
                        "WHERE film_id = :film_id;";

        KeyHolder keyHolder = makeNamedJdbcOperationsUpdate(sql, film);
        return keyHolder.getKeyAs(Integer.class);
    }

    @Override
    public Integer addFilm(Film film) {
        log.debug("Вызов метода addFilm класса FilmDaoImpl film = {}", film);
        String sql = "INSERT INTO films(film_name, description, release_date, duration_in_minutes, mpa_rate_id) " +
                "VALUES (:film_name, :description, :release_date, :duration_in_minutes, :mpa_rate_id)";

        KeyHolder keyHolder = makeNamedJdbcOperationsUpdate(sql, film);
        return keyHolder.getKeyAs(Integer.class);
    }

    @Override
    public List<Film> getFilmsRating(int count) {
        log.debug("Вызов метода getFilmsRating класса FilmDaoImpl count = {}", count);
        String sql =
                "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration_in_minutes, " +
                        "mr.mpa_rate_id, mr.mpa_rate_name, COUNT(l.film_id) AS rating FROM films AS f " +
                        "LEFT JOIN mpa_rates AS mr ON mr.mpa_rate_id = f.mpa_rate_id " +
                        "LEFT JOIN likes AS l ON l.film_id = f.film_id " +
                        "GROUP BY f.film_id ORDER BY rating DESC LIMIT :count";

        return jdbcOperations.query(sql, Map.of("count", count), ((rs, rowNum) -> ModelsParser.parseFilm(rs)));
    }

    private KeyHolder makeNamedJdbcOperationsUpdate(String sql, Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource map = makeMap(film);
        jdbcOperations.update(sql, map, keyHolder);
        return keyHolder;
    }

    private MapSqlParameterSource makeMap(Film film) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("film_id", film.getId());
        map.addValue("film_name", film.getName());
        map.addValue("description", film.getDescription());
        map.addValue("duration_in_minutes", film.getDuration());
        map.addValue("release_date", film.getReleaseDate());
        map.addValue("mpa_rate_id", film.getMpa().getId());
        return map;
    }
}
