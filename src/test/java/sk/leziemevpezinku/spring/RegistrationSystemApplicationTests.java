package sk.leziemevpezinku.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import sk.leziemevpezinku.spring.annotation.IntegrationTest;
import sk.leziemevpezinku.spring.repo.EventRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
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
