package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Identifiable;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public class Service<T extends Identifiable> {

    protected final Storage<T> storage;

    public Service(Storage<T> storage) {
        this.storage = storage;
    }

    public boolean update(T item) {
        if (storage.isContains(item.getId())) {
            storage.update(item);
            return true;
        }
        return false;
    }

    public List<T> getAll() {
        return storage.getAll();
    }

    public T get(int id) {
        T item = storage.get(id);
        if (item == null) {
            throw new NullPointerException("Отсутствует объект с id = " + id);
        }
        return item;
    }

    public void add(T item) {
        storage.add(item);
    }
}
