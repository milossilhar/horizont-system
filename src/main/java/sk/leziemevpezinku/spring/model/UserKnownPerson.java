package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Known person, that can pickup child from the Event.
 */
@Getter
@Setter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserKnownPerson {

    @JsonProperty("name")
    @Column(name = "name", length = 50)
    private String name;

    @JsonProperty("surname")
    @Column(name = "surname", length = 50)
    private String surname;

    // enum - REG_E_RELATION
    @JsonProperty("relation")
    @Column(name = "relation", length = 5)
    private String relation;
}
