package sk.leziemevpezinku.spring.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sk.leziemevpezinku.spring.api.validation.impl.StartBeforeEndValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartBeforeEndValidator.class)
public @interface StartBeforeEnd {
    String message() default "Start must be before end.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String startProperty();
    String endProperty();
}
