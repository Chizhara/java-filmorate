package ru.yandex.practicum.filmorate.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalDateMinValueValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateMinDateConstraint {
    String message() default "Invalid LocalDate time";
    int y();
    int month();
    int d();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}