package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос Post /user  {}", user.toString());
        userService.add(user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос Put /user");
        if (userService.update(user)) {
            return user;
        } else {
            throw new IllegalAccessError("Неверный идентификатор");
        }
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        log.info("Получен запрос Get /user {}", id);
        try {
            return userService.get(id);
        } catch (NullPointerException e) {
            throw new IllegalAccessError(e.getMessage());
        }
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получен запрос Get /user");
        return userService.getAll();
    }
}
