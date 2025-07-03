package sk.leziemevpezinku.spring.api.validation;

import jakarta.validation.Constraint;
import sk.leziemevpezinku.spring.api.validation.impl.ValidRepeatTypeValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidRepeatTypeValidator.class)
public @interface ValidRepeatType {
}
