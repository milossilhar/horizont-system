package sk.leziemevpezinku.spring.api.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import sk.leziemevpezinku.spring.api.validation.EnumerationValue;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;
import sk.leziemevpezinku.spring.repo.EnumerationRepository;

@RequiredArgsConstructor
public class EnumerationValueValidator implements ConstraintValidator<EnumerationValue, String> {

    private final EnumerationRepository enumerationRepository;

    private EnumerationName enumName;

    @Override
    public void initialize(EnumerationValue constraintAnnotation) {
        this.enumName = constraintAnnotation.enumName();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (code == null || code.isEmpty()) return true;

        return enumerationRepository.existsByEnumNameAndCode(this.enumName, code);
    }
}
