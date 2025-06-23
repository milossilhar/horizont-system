package sk.leziemevpezinku.spring.api;

import org.junit.jupiter.api.Test;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.api.mapper.EventTermMapper;
import sk.leziemevpezinku.spring.model.EventTerm;
import sk.leziemevpezinku.spring.model.enums.CapacityStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
public class EventTermMapperTest extends AbstractMapperTest<EventTermMapper> {

    public EventTermMapperTest() {
        super(EventTermMapper.class);
    }

    @Test
    public void testToDTO() {
        var eventTerm = EventTerm.builder()
                .startDate(LocalDate.of(2020, Month.SEPTEMBER, 11))
                .startTime(LocalTime.of(14, 0))
                .endDate(LocalDate.of(2021, Month.FEBRUARY, 12))
                .expectedTrainers(List.of("lenka", "melis"))
                .dayOfWeek(DayOfWeek.MONDAY)
                .numberOfLessons(18L)
                .capacity(20L)
                .build();

        var dto = mapper.toDTO(eventTerm, 4);

        assertEquals(LocalDate.of(2020, Month.SEPTEMBER, 11), dto.getStartDate());
        assertEquals(LocalTime.of(14, 0), dto.getStartTime());
        assertEquals(16, dto.getAvailable());
        assertEquals(CapacityStatus.FREE, dto.getCapacityStatus());
    }
}
