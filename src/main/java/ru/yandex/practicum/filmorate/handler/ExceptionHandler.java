package ru.yandex.practicum.filmorate.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.Map;

@Slf4j
@RestControllerAdvice("ru.yandex.practicum.filmorate.controller")
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        log.info("Ошибка! {}", e.getMessage());
        return Map.of("Ошибка валидации", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleIllegalAccessError(final IllegalAccessError e) {
        log.info("Ошибка! {}", e.getMessage());
        return Map.of("Ошибка. Не найден искомый объект", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntimeException(final RuntimeException e) {
        log.info("Ошибка! {}", e.getMessage());
        return Map.of("Ошибка!", e.getMessage());
    }
}
