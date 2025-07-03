package sk.leziemevpezinku.spring.api;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.api.dto.EventDTO;
import sk.leziemevpezinku.spring.mapper.EventConditionMapper;
import sk.leziemevpezinku.spring.mapper.EventMapper;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.model.EventCondition;
import sk.leziemevpezinku.spring.model.EventTerm;
import sk.leziemevpezinku.spring.api.enumeration.EventConditionType;
import sk.leziemevpezinku.spring.util.TestUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
public class EventMapperTest extends AbstractMapperTest<EventMapper> {

    protected EventMapperTest() {
        super(EventMapper.class);
        TestUtil.setField(mapper, "eventConditionMapper", Mappers.getMapper(EventConditionMapper.class));
    }

    @Test
    public void toDTO() {
        Event event = Event.builder()
                .id(1L)
                .conditions(
                        List.of(EventCondition.builder()
                                .conditionType(EventConditionType.YEAR_BORN.name())
                                .value("12")
                                .build())
                )
                .terms(
                        Set.of(EventTerm.builder()
                                        .capacity(12L)
                                        .durationMinutes(120)
                                        .startDate(LocalDate.of(2020, 1, 1))
                                .build())
                )
                .uuid("test-uuid")
                .name("Test Event")
                .eventType("SUPER_TYPE")
                .build();

        EventDTO dto = mapper.toDTO(event);

        assertEquals(1, dto.getConditions().size());
    }
}
