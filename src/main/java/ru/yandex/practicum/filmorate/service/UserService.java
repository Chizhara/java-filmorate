package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dao.FriendLinkDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exceptions.NoDataFoundException;
import ru.yandex.practicum.filmorate.model.FriendLink;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@org.springframework.stereotype.Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final FriendLinkDao friendLinkDao;

    public User get(int id) {
        Optional<User> item = userDao.findUserById(id);
        if (item.isEmpty()) {
            throw new NoDataFoundException("Отсутствует объект с id = " + id);
        }
        return item.get();
    }

    public List<User> getAll() {
        return userDao.getAllUsers();
    }

    public User add(User user) {
        checkName(user);
        user.setId(Objects.requireNonNull(userDao.addUser(user)));
        return get(user.getId());
    }

    public User update(User user) {
        checkName(user);
        if (userDao.updateUser(user) == null) {
            throw new NoDataFoundException("Пользователь не был найден. Проверьте идентификатор");
        }
        return get(user.getId());
    }

    public void addFriendLink(int userId, int friendId) {
        log.debug("Вызов метода addFriendLink класса UserService userId = {}; friendId = {}", userId, friendId);
        if (userDao.findUserById(userId).isEmpty()) {
            throw new NoDataFoundException("Пользователь не найден с id = " + userId);
        }
        if (userDao.findUserById(friendId).isEmpty()) {
            throw new NoDataFoundException("Пользователь не найден с id = " + friendId);
        }
        Optional<FriendLink> friendLinkOptional = friendLinkDao.getFriendLinkByUsersId(userId, friendId);
        if (friendLinkOptional.isEmpty()) {
            friendLinkDao.createFriendLink(userId, friendId);
        } else if (!friendLinkOptional.get().isFriendByUserId(userId)) {
            friendLinkDao.changeConfirmFriendLink(userId, friendId, true);
        }
    }

    public FriendLink removeFriend(int userId, int friendId) {
        Optional<FriendLink> friendLinkOptional = friendLinkDao.getFriendLinkByUsersId(userId, friendId);
        if (friendLinkOptional.isEmpty()) {
            throw new NoDataFoundException("Такой дружбы нет");
        }
        FriendLink friendLink = friendLinkOptional.get();

        if (friendLink.getInitiator().getId() == userId) {
            friendLinkDao.removeFriendLink(friendLink);
            if (friendLink.isConfirm()) {
                friendLinkDao.createFriendLink(friendId, userId);
            }
        } else if (friendLink.isConfirm()) {
            friendLinkDao.changeConfirmFriendLink(userId, friendId, false);
        }

        return friendLink;
    }

    public List<User> getFriends(int userId) {
        return friendLinkDao.getFriendsOfUserById(userId);
    }

    public List<User> getCommonFriendsList(int id, int otherId) {
        return friendLinkDao.getCommonFriendsList(id, otherId);
    }

    private void checkName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
