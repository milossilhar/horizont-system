package sk.leziemevpezinku.spring.api.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import sk.leziemevpezinku.spring.api.validation.EitherNotNull;

public class EitherNotNullValidator implements ConstraintValidator<EitherNotNull, Object> {

    private String propertyA;
    private String propertyB;

    @Override
    public void initialize(EitherNotNull constraintAnnotation) {
        this.propertyA = constraintAnnotation.propertyA();
        this.propertyB = constraintAnnotation.propertyB();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
        var a = wrapper.getPropertyValue(propertyA);
        var b = wrapper.getPropertyValue(propertyB);

        return (a != null && b == null) ||
                (a == null && b != null);
    }
}
