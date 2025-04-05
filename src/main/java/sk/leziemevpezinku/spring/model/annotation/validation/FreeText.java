package sk.leziemevpezinku.spring.model.annotation.validation;

import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Pattern(regexp = "^[a-zA-ZÀ-ʯ \\-_.,?]+$")
public @interface FreeText {
}
