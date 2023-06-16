package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.FriendLink;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class UserService extends Service<User> {
    @Autowired
    private UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        super(userStorage);
    }

    @Override
    public void add(User user) {
        checkName(user);
        super.add(user);
    }

    @Override
    public boolean update(User user) {
        checkName(user);
        return super.update(user);
    }

    public FriendLink addFriendLink(int userId, int friendId) {
        FriendLink friendLinkAdded = generateFriendLink(userId, friendId);
        userStorage.addFriendLink(friendLinkAdded);
        return friendLinkAdded;
    }

    public FriendLink removeFriendLink(int userId, int friendId) {
        FriendLink friendLinkRemoved = generateFriendLink(userId, friendId);
        userStorage.removeFriendLink(friendLinkRemoved);
        return friendLinkRemoved;
    }

    public List<User> getFriends(int userId) {
        return userStorage.getFriends(userId).stream()
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }

    public Set<User> getCommonFriendsList(int id, int otherId) {
        List<User> friendListA = getFriends(id);
        List<User> friendListB = getFriends(otherId);
        return friendListA.stream().filter(friendListB::contains).collect(Collectors.toSet());
    }

    private void checkName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private FriendLink generateFriendLink(int userAId, int userBId) {
        return new FriendLink(get(userAId), get(userBId));
    }
}
