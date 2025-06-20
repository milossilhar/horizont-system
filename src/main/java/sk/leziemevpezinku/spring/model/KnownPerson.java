package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Known person, that can pickup child from the Event.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KnownPerson {

    @ToString.Include
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ToString.Include
    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    /** Enumerated :: REG_E_RELATION */
    @ToString.Include
    @Column(name = "relation", length = 10, nullable = false)
    private String relation;
}
