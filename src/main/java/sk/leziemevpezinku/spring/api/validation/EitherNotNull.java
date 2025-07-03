package sk.leziemevpezinku.spring.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sk.leziemevpezinku.spring.api.validation.impl.EitherNotNullValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EitherNotNullValidator.class)
public @interface EitherNotNull {
    String message() default "Only one of the properties must be not NULL.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String propertyA();
    String propertyB();
}
