package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Identifiable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service<T extends Identifiable> {

    protected final Map<Integer, T> storage = new HashMap<>();
    private int sec = 0;

    public boolean update(T item) {
        if (storage.containsKey(item.getId())) {
            storage.put(item.getId(), item);
            return true;
        }
        return false;
    }

    public List<T> getAll() {
        return storage.values().stream().collect(Collectors.toUnmodifiableList());
    }

    public void add(T item) {
        sec++;
        storage.put(sec, item);
        item.setId(sec);
    }
}
