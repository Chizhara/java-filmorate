package ru.yandex.practicum.filmorate.annotation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class LocalDateMinValueValidator implements ConstraintValidator<LocalDateMinDateConstraint, LocalDate> {

    LocalDate localDate;

    @Override
    public void initialize(LocalDateMinDateConstraint localDateMinDateConstraint) {
        this.localDate = LocalDate.of(localDateMinDateConstraint.y(), localDateMinDateConstraint.month(),
                localDateMinDateConstraint.d());
    }

    @Override
    public boolean isValid(LocalDate date,
        ConstraintValidatorContext cxt) {
        return date.isAfter(localDate);
    }
}

