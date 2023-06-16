package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Identifiable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStorage<T extends Identifiable> {

    protected final Map<Integer, T> storage = new HashMap<>();

    private int sec = 0;

    public T add(T value) {
        value.setId(++sec);
        return storage.put(value.getId(), value);
    }

    public T update(T value) {
        return storage.put(value.getId(), value);
    }

    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    public T get(int id) {
        return storage.get(id);
    }

    public boolean isContains(int id) {
        return storage.containsKey(id);
    }
}
