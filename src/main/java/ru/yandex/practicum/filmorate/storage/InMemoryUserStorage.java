package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FriendLink;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage extends InMemoryStorage<User> implements UserStorage {
    protected final Set<FriendLink> friendLinks = new HashSet<>();

    @Override
    public boolean isContains(FriendLink friendLink) {
        return friendLinks.contains(friendLink);
    }

    @Override
    public boolean addFriendLink(FriendLink friendLink) {
        return friendLinks.add(friendLink);
    }

    @Override
    public boolean removeFriendLink(FriendLink friendLink) {
        return friendLinks.remove(friendLink);
    }

    @Override
    public List<User> getFriends(int userId) {
        return friendLinks.stream().filter(userLink -> userLink.isContains(userId))
                .map(userLink -> userLink.getFriendByUserId(userId))
                .collect(Collectors.toList());
    }
}
