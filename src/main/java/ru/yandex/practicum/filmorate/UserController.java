package ru.yandex.practicum.filmorate;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.OutOfRangeFilmDescriptionException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.DateTimeException;
import java.time.LocalDate;
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
        try {
            validate(user);
            users.add(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(DateTimeException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }
    }

    @PutMapping
    public ResponseEntity<User> updateFilm(@Valid @RequestBody User user) {
        try {
            log.debug("Получен запрос Put /user");
            Optional<User> userOptional = users.stream().filter(userTemp -> userTemp.getId() == user.getId()).findAny();
            if (userOptional.isPresent()) {
                User userTemp = userOptional.get();
                userTemp.setBirthdate(user.getBirthdate());
                userTemp.setName(user.getName());
                userTemp.setLogin(user.getLogin());
                userTemp.setEmail(user.getEmail());
            } else {
                users.add(user);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (DateTimeException | OutOfRangeFilmDescriptionException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }
    }

    @GetMapping
    public Set<User> getFilms() {
        log.debug("Получен запрос Get /user");
        return users;
    }

    private void validate(User user) {
        if(user.getBirthdate().isAfter(LocalDate.now())) {
            throw new DateTimeException("Дата рождения не может быть в будущем");
        }
        if(user.getName() == null) {
            user.setName(user.getLogin());
        }
    }
}
