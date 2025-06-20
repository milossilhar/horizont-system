package sk.leziemevpezinku.spring.repo;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.model.EventTerm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
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
                .durationMinutes(45)
                .price(BigDecimal.valueOf(400))
                .build();

        var saved = repository.saveAndFlush(eventTerm);

        assertTrue(repository.existsById(saved.getId()));
    }

    @Test
    @DisplayName("saving EventTerm entity with json")
    public void testSaveWithJson() {
        var event = eventRepository.findById(1L);
        var eventTerm =  EventTerm.builder()
                .event(event.orElseThrow())
                .startDate(LocalDate.now())
                .startTime(LocalTime.of(14, 0, 0))
                .durationMinutes(45)
                .price(BigDecimal.valueOf(400))
                .expectedTrainers(List.of("migmig", "melis"))
                .build();

        var saved = repository.saveAndFlush(eventTerm);

        entityManager.clear();

        assertTrue(repository.existsById(saved.getId()));

        var dbEventTerm = repository.findById(saved.getId());
        assertTrue(dbEventTerm.isPresent());

//        assertEquals("").orElseThrow();
        log.info("TESTING logging");
    }
}
