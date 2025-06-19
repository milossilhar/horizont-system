package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Known person, that can pickup child from the Event.
 */
@Data
@Embeddable
public class KnownPerson {

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    /** Enumerated :: REG_E_RELATION */
    @Column(name = "relation", length = 10, nullable = false)
    private String relation;
}
