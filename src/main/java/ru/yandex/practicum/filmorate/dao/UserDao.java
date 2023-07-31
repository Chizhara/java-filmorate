package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findUserById(int id);

    List<User> getAllUsers();

    Integer updateUser(User user);

    Integer addUser(User user);
}
