package sk.leziemevpezinku.spring.repo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.api.enumeration.EventTermRepeatType;
import sk.leziemevpezinku.spring.model.EventTerm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
public class EventTermRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private EventTermRepository repository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("saving EventTerm entity")
    public void testSave() {
        var event = eventRepository.findById(1L);
        var eventTerm =  EventTerm.builder()
                .event(event.orElseThrow())
                .startDate(LocalDate.now())
                .startTime(LocalTime.of(14, 0, 0))
                .repeatType(EventTermRepeatType.ONCE)
                .durationMinutes(45)
                .capacity(10L)
                .price(BigDecimal.valueOf(400))
                .build();

        var saved = repository.saveAndFlush(eventTerm);

        assertTrue(repository.existsById(saved.getId()));
    }

    @Test
    @DisplayName("saving EventTerm entity with json")
    public void testSaveWithJson() {
        var event = eventRepository.findById(1L);
        var expected = List.of("migmig", "melis");
        var eventTerm =  EventTerm.builder()
                .event(event.orElseThrow())
                .startDate(LocalDate.now())
                .startTime(LocalTime.of(14, 0, 0))
                .repeatType(EventTermRepeatType.ONCE)
                .durationMinutes(45)
                .capacity(10L)
                .price(BigDecimal.valueOf(400))
                .expectedTrainers(expected)
                .build();

        var saved = repository.saveAndFlush(eventTerm);
        entityManager.clear();

        assertTrue(repository.existsById(saved.getId()));

        var dbEventTerm = repository.findById(saved.getId());
        assertTrue(dbEventTerm.isPresent());

        assertLinesMatch(expected, dbEventTerm.orElseThrow().getExpectedTrainers());
    }
}
