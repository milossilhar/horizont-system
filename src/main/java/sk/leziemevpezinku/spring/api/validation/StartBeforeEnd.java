package sk.leziemevpezinku.spring.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sk.leziemevpezinku.spring.api.validation.impl.StartBeforeEndValidator;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(StartBeforeEnd.List.class)
@Constraint(validatedBy = StartBeforeEndValidator.class)
public @interface StartBeforeEnd {
    String message() default "Start must be before end.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String startProperty();
    String endProperty();


    @Target(TYPE)
    @Retention(RUNTIME)
    @interface List {
        StartBeforeEnd[] value();
    }
}
