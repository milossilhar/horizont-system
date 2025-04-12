package sk.leziemevpezinku.spring.model;

import org.junit.jupiter.api.Test;
import sk.leziemevpezinku.spring.annotation.UnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
public class PersonTest {

    @Test
    public void testFullname() {
        Person person = Person.builder()
                .name("peter")
                .surname("griffin")
                .build();

        assertEquals("peter griffin", person.getFullName());
    }
}
