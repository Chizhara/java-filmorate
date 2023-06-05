package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalDateMinValueValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateMinDateConstraint {
    String message() default "Invalid LocalDate time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}