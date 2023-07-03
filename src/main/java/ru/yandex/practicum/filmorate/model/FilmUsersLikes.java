package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class FilmUsersLikes {
    private final Set<User> users;
    @Setter
    private Film film;

    public FilmUsersLikes(Film film) {
        this.film = film;
        users = new HashSet<>();
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    public int getLikesCount() {
        return users.size();
    }
}
