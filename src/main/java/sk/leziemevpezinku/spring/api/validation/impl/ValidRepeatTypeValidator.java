package sk.leziemevpezinku.spring.api.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sk.leziemevpezinku.spring.api.EventTermDTO;
import sk.leziemevpezinku.spring.api.validation.ValidRepeatType;
import sk.leziemevpezinku.spring.model.enums.EventTermRepeatType;

public class ValidRepeatTypeValidator implements ConstraintValidator<ValidRepeatType, EventTermDTO> {

    @Override
    public boolean isValid(EventTermDTO dto, ConstraintValidatorContext context) {
        if (dto == null) return true;

        var repeatType = dto.getRepeatType();
        if (repeatType == null) return true;

        if (EventTermRepeatType.ONCE.equals(repeatType)) return true;
        if (EventTermRepeatType.DAILY.equals(repeatType)) {
            return dto.getEndDate() != null;
        }
        if (EventTermRepeatType.WEEKLY.equals(repeatType)) {
            return dto.getEndDate() != null && dto.getDayOfWeek() != null;
        }
        return true;
    }
}
