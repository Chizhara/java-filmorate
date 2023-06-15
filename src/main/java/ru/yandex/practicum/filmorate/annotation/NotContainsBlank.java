package ru.yandex.practicum.filmorate.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotContainsBlankValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotContainsBlank {
    String message() default "Invalid LocalDate time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}