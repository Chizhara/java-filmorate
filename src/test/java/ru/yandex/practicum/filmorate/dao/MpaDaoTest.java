package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dao.impl.MpaRateDaoImpl;
import ru.yandex.practicum.filmorate.model.MpaRate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import({MpaRateDaoImpl.class})
public class MpaDaoTest {
    static List<MpaRate> mpaRates;
    @Autowired
    MpaRateDao mpaRateDao;

    @BeforeAll
    public static void beforeAll() {
        mpaRates = List.of(new MpaRate(1, "G"), new MpaRate(2, "PG"),
                new MpaRate(3, "PG-13"), new MpaRate(4, "R"),
                new MpaRate(5, "NC-17"));
    }

    @Test
    public void shouldReturnCorrectMpaRatesList() {
        List<MpaRate> mpaRatesTest = mpaRateDao.getMpaRates();
        assertEquals(mpaRatesTest, mpaRates);
    }

    @Test
    public void shouldReturnMpaRateG() {
        Optional<MpaRate> mpaOptional = mpaRateDao.findMpaById(1);
        assertTrue(mpaOptional.isPresent());
        assertEquals(mpaOptional.get(), mpaRates.get(0));
    }
}
