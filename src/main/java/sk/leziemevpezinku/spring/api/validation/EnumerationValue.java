package sk.leziemevpezinku.spring.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sk.leziemevpezinku.spring.api.validation.impl.EnumerationValueValidator;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumerationValueValidator.class)
public @interface EnumerationValue {
    String message() default "Invalid enum value.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    EnumerationName enumName();
}
