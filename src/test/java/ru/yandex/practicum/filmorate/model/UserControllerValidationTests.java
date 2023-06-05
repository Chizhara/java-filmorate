package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerValidationTests {

    @Autowired
    Validator validator;

    @Test
    public void validateCorrectUser() {
        User user = User.builder().id(1).name("User").email("asd@email.com").login("login")
                .birthday(LocalDate.of(2000,1,1)).build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldReturnUserWithLoginInName() {
        User user = User.builder().id(1).name(null).email("asd@email.com").login("login")
                .birthday(LocalDate.of(2000,1,1)).build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldNotValidateUserWithBirthdateInFuture() {
        User user = User.builder().id(1).name("User").email("asd@email.com").login("login")
                .birthday(LocalDate.now().plusYears(1)).build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldNotValidateUserWithWorseEmail() {
        User user = User.builder().id(1).name("User").email("asd@").login("login")
                .birthday(LocalDate.of(2000,1,1)).build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldNotValidateUserWithEmptyLogin() {
        User user = User.builder().id(1).name("User").email("asd@email.com").login(" ")
                .birthday(LocalDate.of(2000,1,1)).build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldNotValidateUserWithBlankInLogin() {
        User user = User.builder().id(1).name("User").email("asd@email.com").login("User One")
                .birthday(LocalDate.of(2000,1,1)).build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
}
