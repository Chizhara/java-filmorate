package ru.yandex.practicum.filmorate.annotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class LocalDateMinValueValidator implements ConstraintValidator<LocalDateMinDateConstraint, LocalDate> {

    @Override
    public void initialize (LocalDateMinDateConstraint localDate){
    }

    @Override
    public boolean isValid (LocalDate date,
        ConstraintValidatorContext cxt){
        return date.isAfter(LocalDate.of(1985, 11, 28));
    }
}

