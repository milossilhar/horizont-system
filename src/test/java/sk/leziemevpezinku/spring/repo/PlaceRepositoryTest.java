package sk.leziemevpezinku.spring.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.model.Place;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
public class PlaceRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private PlaceRepository repository;

    @Test
    public void testSave() {
        var p = Place.builder()
                .name("SQUASH Pezinok")
                .latitude(BigDecimal.valueOf(12))
                .longitude(BigDecimal.valueOf(24))
                .build();

        var saved = repository.saveAndFlush(p);

        var id = saved.getId();
        assertNotNull(id);

        var dbPeriod = repository.findById(id);
        assertTrue(dbPeriod.isPresent());
        assertEquals(id, dbPeriod.get().getId());
    }
}
