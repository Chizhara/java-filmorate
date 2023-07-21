package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.MpaRate;

import java.util.List;
import java.util.Optional;

public interface MpaRateDao {
    Optional<MpaRate> findMpaById(int mpaId);

    List<MpaRate> getMpaRates();
}
