package sk.leziemevpezinku.spring.model.annotation.validation;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Size(min = 5, max = 20)
@Pattern(regexp = "^[+]?\\d+$")
public @interface TelephoneNumber { }
