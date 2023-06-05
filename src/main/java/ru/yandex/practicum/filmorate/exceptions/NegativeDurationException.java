package ru.yandex.practicum.filmorate.exceptions;

public class NegativeDurationException extends RuntimeException {
    public NegativeDurationException(String message, int duration) {
        super(message + ": " + duration);
    }
}