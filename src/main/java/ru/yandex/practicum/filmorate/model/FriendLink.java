package ru.yandex.practicum.filmorate.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public class FriendLink {

    private final User userA;
    private final User userB;

    public FriendLink(User userA, User userB) {
        if (userA == null || userB == null) {
            throw new IllegalAccessError("Передан неверный идентификатор пользователя");
        }
        if (userA.equals(userB)) {
            throw new IllegalAccessError();
        }
        this.userA = userA;
        this.userB = userB;
    }

    public boolean isContains(int userId) {
        return userA.getId() == userId || userB.getId() == userId;
    }

    public User getUserById(int userId) {
        return userA.getId() == userId ? userA : userB.getId() == userId ? userB : null;
    }

    public User getFriendByUserId(int userId) {
        return userA.getId() == userId ? userB : userB.getId() == userId ? userA : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendLink that = (FriendLink) o;
        return userA.equals(that.userA) || userB.equals(that.userB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userA, userB);
    }
}
