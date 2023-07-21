package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.LikeDao;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeDaoImpl implements LikeDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void addLike(int userId, int filmId) {
        log.debug("Вызов метода addLike класса LikeDaoImpl userId = {}, filmId = {}", userId, filmId);
        String sql = "INSERT INTO likes(user_id, film_id) VALUES(:user_id, :film_id)";
        jdbcOperations.update(sql, new MapSqlParameterSource(Map.of("user_id", userId, "film_id", filmId)));
    }

    @Override
    public void removeLike(int userId, int filmId) {
        log.debug("Вызов метода removeLike класса LikeDaoImpl userId = {}, filmId = {}", userId, filmId);
        String sql = "DELETE FROM likes WHERE user_id = :user_id AND film_id = :film_id";
        jdbcOperations.update(sql, Map.of("user_id", userId, "film_id", filmId));
    }
}
