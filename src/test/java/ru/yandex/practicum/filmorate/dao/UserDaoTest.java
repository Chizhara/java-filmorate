package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dao.impl.UserDaoImpl;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import({UserDaoImpl.class})
public class UserDaoTest {

    private static User userTestA;
    private static User userTestB;
    @Autowired
    UserDaoImpl userDao;

    @BeforeEach
    public void beforeEach() {
        userTestA = User.builder()
                .id(1)
                .name("Nick")
                .login("login")
                .email("some@mail.ru")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();
        userTestB = User.builder()
                .id(4)
                .name("Nick Name")
                .login("dolore")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
    }

    @Test
    public void shouldReturn2AfterAddUser() {
        int id = userDao.addUser(userTestB);
        assertEquals(userTestB.getId(), id);
    }

    @Test
    public void shouldReturnUserDataSearchedById() {
        Optional<User> userOptional = userDao.findUserById(userTestA.getId());
        assertTrue(userOptional.isPresent());
        assertEquals(userTestA, userOptional.get());
    }

    @Test
    public void shouldUpdateUserData() {
        userTestA.setName("Name Nick");
        userTestA.setLogin("nick");
        userTestA.setEmail("yandex@mail.ru");
        userTestA.setBirthday(LocalDate.of(1964, 4, 2));
        userDao.updateUser(userTestA);
        Optional<User> userOptional = userDao.findUserById(userTestA.getId());
        assertTrue(userOptional.isPresent());
        assertEquals(userTestA, userOptional.get());
    }
}
