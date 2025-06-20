package sk.leziemevpezinku.spring.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.model.Period;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
public class PeriodRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private PeriodRepository repository;

    @Test
    public void testSave() {
        var p = Period.builder()
                .name("Jar 2025")
                .build();

        var saved = repository.saveAndFlush(p);

        var id = saved.getId();
        assertNotNull(id);

        assertTrue(repository.existsById(id));
    }
}
