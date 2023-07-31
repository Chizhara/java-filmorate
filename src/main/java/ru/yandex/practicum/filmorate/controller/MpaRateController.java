package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.NoDataFoundException;
import ru.yandex.practicum.filmorate.model.MpaRate;
import ru.yandex.practicum.filmorate.service.MpaRateService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaRateController {

    @Autowired
    MpaRateService mpaRateService;

    @GetMapping("/{mpaId}")
    public MpaRate getMpaById(@PathVariable int mpaId) {
        log.info("Получен запрос Get /mpa/{}  ", mpaId);
        try {
            return mpaRateService.getMpaById(mpaId);
        } catch (NoDataFoundException e) {
            throw new IllegalAccessError(e.getMessage());
        }
    }

    @GetMapping
    public List<MpaRate> getAllMpa() {
        log.info("Получен запрос Get /mpa");
        return mpaRateService.getAllMpaRates();
    }
}
