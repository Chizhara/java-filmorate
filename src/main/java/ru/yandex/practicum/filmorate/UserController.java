package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    Set<User> users = new HashSet<>();

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.debug("Получен запрос Post /user");
        checkName(user);
        users.add(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateFilm(@Valid @RequestBody User user) {
        log.debug("Получен запрос Put /user");
        Optional<User> userOptional = users.stream().filter(userTemp -> userTemp.getId() == user.getId()).findAny();
        if(userOptional.isPresent()) {
            User userTemp = userOptional.get();
            userTemp.setBirthdate(user.getBirthdate());
            userTemp.setName(user.getName());
            userTemp.setLogin(user.getLogin());
            userTemp.setEmail(user.getEmail());
        } else {
            users.add(user);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public Set<User> getFilms() {
        log.debug("Получен запрос Get /user");
        return users;
    }

    private void checkName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
