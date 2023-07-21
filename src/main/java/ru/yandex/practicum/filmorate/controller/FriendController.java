package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NoDataFoundException;
import ru.yandex.practicum.filmorate.model.FriendLink;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users/{id}/friends")
@Slf4j
public class FriendController {
    @Autowired
    private UserService userService;

    @PutMapping("/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос Put /users/{}/friends {}", id, friendId);
        try {
            userService.addFriendLink(id, friendId);
        } catch (NoDataFoundException e) {
            throw new IllegalAccessError(e.getMessage());
        }
    }

    @DeleteMapping("/{friendId}")
    public FriendLink removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос Delete /users/{}/friends {}", id, friendId);
        return userService.removeFriend(id, friendId);
    }

    @GetMapping
    public List<User> getFriends(@PathVariable int id) {
        log.info("Получен запрос Get /users/{}/friends ", id);
        return userService.getFriends(id);
    }

    @GetMapping("/common/{otherId}")
    public List<User> getCommonFriendList(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен запрос Get /users/{}/friends {}", id, otherId);
        return userService.getCommonFriendsList(id, otherId);
    }
}
