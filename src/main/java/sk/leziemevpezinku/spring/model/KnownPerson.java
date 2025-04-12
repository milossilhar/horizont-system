package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sk.leziemevpezinku.spring.model.annotation.validation.AccentedName;
import sk.leziemevpezinku.spring.model.annotation.validation.Enumeration;

/**
 * Known person, that can pickup child from the Event.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KnownPerson {

    @NotNull
    @AccentedName
    @JsonProperty("name")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @AccentedName
    @JsonProperty("surname")
    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    // enum - REG_E_RELATION
    @NotNull
    @Enumeration
    @JsonProperty("relation")
    @Column(name = "relation", length = 10, nullable = false)
    private String relation;
}
