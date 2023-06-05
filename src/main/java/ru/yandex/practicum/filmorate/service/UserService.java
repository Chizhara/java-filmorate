package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

public class UserService extends Service<User> {

    @Override
    public void add(User user) {
        checkName(user);
        super.add(user);
    }

    @Override
    public boolean update(User user) {
        checkName(user);
        return super.update(user);
    }

    private void checkName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
