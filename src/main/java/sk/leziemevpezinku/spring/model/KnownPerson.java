package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Represents a known person associated with a user.
 * This class is embeddable and can be used as part of another database entity.
 * The data includes essential information such as name, surname, and a relation code.
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

    /** Enumerated: REG_RELATION */
    @ToString.Include
    @Column(name = "relation_code", length = 10, nullable = false)
    private String relationCode;
}
