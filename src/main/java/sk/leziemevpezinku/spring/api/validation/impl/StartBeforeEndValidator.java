package sk.leziemevpezinku.spring.api.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import sk.leziemevpezinku.spring.api.validation.StartBeforeEnd;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, Object> {

    private String startProperty;
    private String endProperty;

    @Override
    public void initialize(StartBeforeEnd constraintAnnotation) {
        this.startProperty = constraintAnnotation.startProperty();
        this.endProperty = constraintAnnotation.endProperty();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
        var start = wrapper.getPropertyValue(startProperty);
        var end = wrapper.getPropertyValue(endProperty);

        if (start == null || end == null) return true;

        if (start instanceof ChronoLocalDate && end instanceof ChronoLocalDate) {
            return ((ChronoLocalDate) start).isBefore((ChronoLocalDate) end);
        }

        if (start instanceof ChronoLocalDateTime<?> && end instanceof ChronoLocalDateTime<?>) {
            return ((ChronoLocalDateTime<?>) start).isBefore((ChronoLocalDateTime<?>) end);
        }

        return false;
    }
}
