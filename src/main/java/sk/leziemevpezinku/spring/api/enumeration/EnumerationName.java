package sk.leziemevpezinku.spring.api.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sk.leziemevpezinku.spring.api.dto.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.dto.EventConditionTypeDTO;
import sk.leziemevpezinku.spring.api.dto.PlaceDTO;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum EnumerationName {
    REG_EVENT_CONDITION_TYPE(true, true, EventConditionTypeDTO.class),
    REG_EVENT_DISCOUNT_TYPE(false, false, EnumerationItemDTO.class),
    REG_EVENT_TYPE(false, true, EnumerationItemDTO.class),
    REG_EVENT_TERM_TAG(true, true, EnumerationItemDTO.class),
    REG_PLACE(true, true, PlaceDTO.class),
    REG_PERIOD(true, true, EnumerationItemDTO.class),
    REG_RELATION(false, true, EnumerationItemDTO.class),
    REG_SHIRT_SIZE(true, true, EnumerationItemDTO.class);

    private final boolean administrated;
    private final boolean publicVisible;
    private final Class<?> dtoClass;

    public static List<EnumerationName> administrated() {
        return Arrays.stream(EnumerationName.values())
                .filter(EnumerationName::isAdministrated)
                .toList();
    }

    public static List<EnumerationName> publiclyVisible() {
        return Arrays.stream(EnumerationName.values())
                .filter(EnumerationName::isPublicVisible)
                .toList();
    }

    public interface Names {
        String REG_EVENT_CONDITION_TYPE = "REG_EVENT_CONDITION_TYPE";
        String REG_EVENT_DISCOUNT_TYPE = "REG_EVENT_DISCOUNT_TYPE";
        String REG_EVENT_TYPE = "REG_EVENT_TYPE";
        String REG_EVENT_TERM_TAG = "REG_EVENT_TERM_TAG";
        String REG_PLACE = "REG_PLACE";
        String REG_PERIOD = "REG_PERIOD";
        String REG_RELATION = "REG_RELATION";
        String REG_SHIRT_SIZE = "REG_SHIRT_SIZE";
    }
}
