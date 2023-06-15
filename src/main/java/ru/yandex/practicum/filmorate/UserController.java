package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    UserService userService = new UserService();

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
            log.info("Ошибка! передан неизвестный фильм");
            throw new IllegalAccessError();
        }
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получен запрос Get /user");
        return userService.getAll();
    }
}
