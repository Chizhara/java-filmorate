package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaRateDao;
import ru.yandex.practicum.filmorate.model.MpaRate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MpaRateDaoImpl implements MpaRateDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<MpaRate> findMpaById(int mpaId) {
        log.debug("Вызов метода findMpaById класса MpaRateDaoImpl mpaId = {}", mpaId);
        String sql = "SELECT * FROM mpa_rates WHERE mpa_rate_id = :mpa_id";
        List<MpaRate> mpaRates = jdbcOperations.query(sql, Map.of("mpa_id", mpaId),
                (rs, rowNum) -> ModelsParser.parseMpaRate(rs));
        if (mpaRates.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(mpaRates.get(0));
    }

    @Override
    public List<MpaRate> getMpaRates() {
        log.debug("Вызов метода getMpaRates класса MpaRateDaoImpl");
        String sql = "SELECT * FROM mpa_rates";

        return jdbcOperations.query(sql, (rs, rowNum) -> ModelsParser.parseMpaRate(rs));
    }
}
