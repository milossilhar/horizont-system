package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.parameters.P;
import sk.leziemevpezinku.spring.model.annotation.validation.AccentedName;
import sk.leziemevpezinku.spring.model.annotation.validation.TelephoneNumber;
import sk.leziemevpezinku.spring.model.base.AuditedCreationEntityBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_user")
public class User extends AuditedCreationEntityBase {

    @Id
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_id")
    @SequenceGenerator(name = "seq_user_id", sequenceName = "seq_user_id", initialValue = 1, allocationSize = 1)
    private Long id;

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

    @NotNull
    @Email
    @JsonProperty("email")
    @Column(name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @NotNull
    @TelephoneNumber
    @JsonProperty("tel_phone")
    @Column(name = "tel_phone", length = 20, nullable = false)
    private String telPhone;

    @JsonManagedReference
    @JsonProperty("people")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Person> people = new ArrayList<>();

    @JsonManagedReference
    @JsonProperty("payments")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();

    @JsonProperty("known_people")
    @OrderColumn(name = "ind")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reg_user_known_person", joinColumns = @JoinColumn(name = "user_id"))
    private List<UserKnownPerson> knownPeople = new ArrayList<>();

    /**
     * checks if given person is in this users people, uses {@link Person} isEqual method.
     * @param person person to check
     * @return true if people contain given person, false otherwise
     */
    public boolean hasPerson(Person person) {
        if (person == null) return false;
        if (people.isEmpty()) return false;

        return people.stream().anyMatch(p -> p.isEqual(person));
    }

    /**
     * Gets same person from people list, uses {@link Person} isEqual method.
     * @param person person to find
     * @return same person from this users people, empty optional otherwise
     */
    public Optional<Person> getPerson(Person person) {
        if (person == null) return Optional.empty();

        return people.stream().filter(p -> p.isEqual(person)).findFirst();
    }
}
