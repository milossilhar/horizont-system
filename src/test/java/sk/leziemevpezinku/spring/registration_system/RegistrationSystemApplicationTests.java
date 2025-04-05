package sk.leziemevpezinku.spring.registration_system;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sk.leziemevpezinku.spring.repo.EventRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class RegistrationSystemApplicationTests {

    @Value("${horizon.environment}")
    private String environment;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void contextLoads() {
        // checks if context is properly loaded
        assertEquals("test", environment);
    }

    @Test
    public void testEventRepo() {
        long events = eventRepository.count();
        assertEquals(1L, events);
    }
}
