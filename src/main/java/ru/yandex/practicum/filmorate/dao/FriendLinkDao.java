package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.FriendLink;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface FriendLinkDao {
    void createFriendLink(int userId, int friendId);

    void changeConfirmFriendLink(int userId, int friendId, boolean confirm);

    Optional<FriendLink> getFriendLinkByUsersId(int userId, int friendId);

    List<User> getFriendsOfUserById(int userId);

    void removeFriendLink(FriendLink friendLink);

    List<User> getCommonFriendsList(int userId, int commonUserId);
}
