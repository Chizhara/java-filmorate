package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
public class FriendLink {

    @NotNull
    private final User initiator;
    @NotNull
    private final User recipient;
    @Setter
    private boolean confirm;

    public FriendLink(User userA, User userB) {
        this(userA, userB, false);
    }

    public FriendLink(User initiator, User recipient, boolean confirm) {
        if (initiator.equals(recipient)) {
            throw new IllegalArgumentException("Передан неверный идентификатор друга");
        }
        this.initiator = initiator;
        this.recipient = recipient;
        this.confirm = confirm;
    }

    public boolean isContains(int userId) {
        return initiator.getId() == userId || recipient.getId() == userId;
    }

    public boolean isFriendByUserId(int userId) {
        if (userId == initiator.getId()) {
            return true;
        } else {
            return (userId == recipient.getId() && confirm);
        }
    }

    public User getUserById(int userId) {
        return initiator.getId() == userId ? initiator : recipient.getId() == userId ? recipient : null;
    }

    public User getFriendByUserId(int userId) {
        return initiator.getId() == userId ? recipient : recipient.getId() == userId ? initiator : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendLink that = (FriendLink) o;
        return initiator.equals(that.initiator) || recipient.equals(that.recipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initiator, recipient);
    }
}
