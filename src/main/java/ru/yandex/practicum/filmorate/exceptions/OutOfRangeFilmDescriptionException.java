package ru.yandex.practicum.filmorate.exceptions;

public class OutOfRangeFilmDescriptionException extends RuntimeException {
    public OutOfRangeFilmDescriptionException(String message, int exceptedValue, int receivedValue) {
        super(message + ". Ожидаемое: " + exceptedValue + "; Полученное: " + receivedValue);
    }
}
