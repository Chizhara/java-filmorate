package ru.yandex.practicum.filmorate.exceptions;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String msg) {
        super(msg);
    }
}
