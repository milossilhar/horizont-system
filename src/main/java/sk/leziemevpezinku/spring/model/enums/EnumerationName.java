package sk.leziemevpezinku.spring.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sk.leziemevpezinku.spring.api.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.PlaceDTO;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum EnumerationName {
    REG_EVENT_CONDITION_TYPE(true, true, EnumerationItemDTO.class),
    REG_EVENT_DISCOUNT_TYPE(true, false, EnumerationItemDTO.class),
    REG_EVENT_TYPE(true, true, EnumerationItemDTO.class),
    REG_PLACE(true, true, PlaceDTO.class),
    REG_RELATION(true, true, EnumerationItemDTO.class),
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
        String REG_PLACE = "REG_PLACE";
        String REG_RELATION = "REG_RELATION";
        String REG_SHIRT_SIZE = "REG_SHIRT_SIZE";
    }
}
