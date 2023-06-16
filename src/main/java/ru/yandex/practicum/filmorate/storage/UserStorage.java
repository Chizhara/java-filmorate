package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.FriendLink;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface UserStorage extends Storage<User> {
    Set<FriendLink> friendLinks = new HashSet<>();

    boolean isContains(FriendLink friendLink);

    boolean addFriendLink(FriendLink friendLink);

    boolean removeFriendLink(FriendLink friendLink);

    List<User> getFriends(int userId);
}
