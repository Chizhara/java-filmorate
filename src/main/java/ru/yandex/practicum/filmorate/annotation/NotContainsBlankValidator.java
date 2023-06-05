package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotContainsBlankValidator implements ConstraintValidator<NotContainsBlank, String> {

    @Override
    public void initialize(NotContainsBlank containsBlank) {
    }

    @Override
    public boolean isValid(String line,
                           ConstraintValidatorContext cxt) {
        return !line.contains(" ");
    }
}
