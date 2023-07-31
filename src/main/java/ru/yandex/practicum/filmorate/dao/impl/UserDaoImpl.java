package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDaoImpl implements UserDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<User> findUserById(int id) {
        log.debug("Вызов метода findUserById класса UserDaoImpl id = {}", id);
        final String sql = "SELECT * FROM users WHERE user_id = :user_id";
        List<User> users = jdbcOperations.query(sql, Map.of("user_id", id),
                (rs, rowNum) -> ModelsParser.parseUser(rs));
        if (users.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));

    }

    @Override
    public List<User> getAllUsers() {
        log.debug("Вызов метода getAllUsers класса UserDaoImpl");
        final String sql = "SELECT * FROM users";
        return jdbcOperations.query(sql, (rs, rowNum) -> ModelsParser.parseUser(rs));
    }

    @Override
    public Integer updateUser(User user) {
        log.debug("Вызов метода updateUser класса UserDaoImpl id = {}", user);
        final String sql = "UPDATE users " +
                "SET user_name = :user_name, login = :login, email = :email, birthday = :birthday " +
                "WHERE user_id = :user_id";
        System.out.println(makeNamedJdbcOperationsUpdate(sql, user).getKeyAs(Integer.class));
        return makeNamedJdbcOperationsUpdate(sql, user).getKeyAs(Integer.class);
    }

    @Override
    public Integer addUser(User user) {
        log.debug("Вызов метода addUser класса UserDaoImpl id = {}", user);
        final String sql = "INSERT INTO users(user_name, login, email, birthday) " +
                "VALUES (:user_name, :login, :email, :birthday)";
        return makeNamedJdbcOperationsUpdate(sql, user).getKeyAs(Integer.class);
    }

    private KeyHolder makeNamedJdbcOperationsUpdate(String sql, User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource map = makeMap(user);
        jdbcOperations.update(sql, map, keyHolder);
        return keyHolder;
    }

    private MapSqlParameterSource makeMap(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("user_name", user.getName());
        map.addValue("login", user.getLogin());
        map.addValue("email", user.getEmail());
        map.addValue("birthday", user.getBirthday());
        map.addValue("user_id", user.getId());
        return map;
    }
}
