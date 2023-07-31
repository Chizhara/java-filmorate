package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dao.impl.FriendLinkDaoImpl;
import ru.yandex.practicum.filmorate.dao.impl.UserDaoImpl;
import ru.yandex.practicum.filmorate.model.FriendLink;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import({UserDaoImpl.class, FriendLinkDaoImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FriendLinkDaoTest {
    private static User userTestA;
    private static User userTestB;
    private static User userTestC;
    @Autowired
    FriendLinkDao friendLinkDao;
    @Autowired
    UserDao userDao;

    @BeforeAll
    public static void beforeAll() {
        userTestA = User.builder()
                .id(1)
                .name("Nick")
                .login("login")
                .email("some@mail.ru")
                .birthday(LocalDate.of(1999, 9, 9))
                .build();

        userTestB = User.builder()
                .id(2)
                .name("Robin")
                .login("password")
                .email("another@mail.ru")
                .birthday(LocalDate.of(2000, 9, 9))
                .build();

        userTestC = User.builder()
                .id(3)
                .name("Jo")
                .login("pass")
                .email("nnwww@mail.ru")
                .birthday(LocalDate.of(2001, 9, 9))
                .build();
    }

    @Test
    @Order(1)
    public void getFriendsOfUserId2() {
        List<User> friends = friendLinkDao.getFriendsOfUserById(2);
        assertFalse(friends.isEmpty());
        assertTrue(friends.contains(userTestA));
    }

    @Test
    @Order(2)
    public void changeConfirm() {
        friendLinkDao.changeConfirmFriendLink(1, 2, true);
        List<User> friends = friendLinkDao.getFriendsOfUserById(1);
        assertFalse(friends.isEmpty());
        assertTrue(friends.contains(userTestB));
    }

    @Test
    @Order(3)
    public void removeConfirm() {
        FriendLink friendLink = new FriendLink(userTestB, userTestA, true);
        friendLinkDao.removeFriendLink(friendLink);
        List<User> friends = friendLinkDao.getFriendsOfUserById(userTestB.getId());
        assertTrue(friends.isEmpty());
    }

    @Test
    @Order(4)
    public void getCommonFriends() {
        friendLinkDao.createFriendLink(userTestA.getId(), userTestC.getId());
        friendLinkDao.createFriendLink(userTestB.getId(), userTestC.getId());

        List<User> friends = friendLinkDao.getCommonFriendsList(userTestA.getId(), userTestB.getId());
        assertFalse(friends.isEmpty());
        assertTrue(friends.contains(userTestC));
    }
}
