package ru.yandex.practicum.filmorate.dao;

public interface LikeDao {
    void addLike(int userId, int filmId);

    void removeLike(int userId, int filmId);
}
