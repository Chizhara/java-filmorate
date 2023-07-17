package ru.yandex.practicum.filmorate.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.util.*;

@Component
@AllArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcOperations jdbcOperations;

    @Override
    public Optional<User> findUserById(int id) {
        final String sql = "SELECT * FROM users WHERE user_id = ?";
        SqlRowSet userRowSet = jdbcOperations.queryForRowSet(sql);
        User user = parseUserFromRow(userRowSet);
        return Optional.of(user);
    }

    @Override
    public List<User> getAllUsers() {
        final String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        SqlRowSet usersRowSet = jdbcOperations.queryForRowSet(sql);
        System.out.println(usersRowSet);
        if(usersRowSet.next()) {
            users.add(parseUserFromRow(usersRowSet));
        }
        return users;
    }

    @Override
    public boolean updateUser(User user) {
        final String sql = "UPDATE users WHERE user_id = ?";
        jdbcOperations.queryForRowSet(sql, user.getId());
        return true;
    }

    @Override
    public void addUser(User user) {
        final String sql = "INSERT INTO users(user_name, login, email, birthday) WHERE user_id = ?";
        jdbcOperations.queryForRowSet(sql, user.getLogin(), user.getEmail(), Date.valueOf(user.getBirthday()));
    }

    private User parseUserFromRow(SqlRowSet rs) {
        Date birthday = rs.getDate("birthday");
        return User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("user_name"))
                .login(rs.getString("login"))
                .email(rs.getString("email"))
                .birthday(birthday == null ? null : birthday.toLocalDate())
                .build();
    }
}
