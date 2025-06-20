package sk.leziemevpezinku.spring.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.model.Period;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
public class PeriodRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private PeriodRepository periodRepository;

    @Test
    public void testSave() {
        Period p = Period.builder()
                .name("Jar 2025")
                .build();

        Period saved = periodRepository.saveAndFlush(p);

        Long id = saved.getId();
        assertNotNull(id);

        Optional<Period> dbPeriod = periodRepository.findById(id);
        assertTrue(dbPeriod.isPresent());
        assertEquals(id, dbPeriod.get().getId());
    }
}
