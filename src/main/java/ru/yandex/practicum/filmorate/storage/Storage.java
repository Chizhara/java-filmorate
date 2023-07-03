package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface Storage<T> {
    T add(T item);

    T update(T item);

    T get(int id);

    List<T> getAll();

    boolean isContains(int id);
}
