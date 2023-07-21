package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaRateDao;
import ru.yandex.practicum.filmorate.exceptions.NoDataFoundException;
import ru.yandex.practicum.filmorate.model.MpaRate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MpaRateService {

    private final MpaRateDao mpaRateDao;

    public MpaRate getMpaById(int mpaId) {
        Optional<MpaRate> mpaRateOptional = mpaRateDao.findMpaById(mpaId);
        if (mpaRateOptional.isEmpty()) {
            throw new NoDataFoundException("Отсутствует MpaRate с id = " + mpaId);
        }
        return mpaRateOptional.get();
    }

    public List<MpaRate> getAllMpaRates() {
        return mpaRateDao.getMpaRates();
    }
}
