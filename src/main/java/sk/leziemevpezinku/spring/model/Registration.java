package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sk.leziemevpezinku.spring.model.annotation.validation.AccentedName;
import sk.leziemevpezinku.spring.model.annotation.validation.TelephoneNumber;
import sk.leziemevpezinku.spring.model.base.UidAuditedEntityBase;
import sk.leziemevpezinku.spring.model.enums.RegistrationStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_registration")
public class Registration extends UidAuditedEntityBase {

    @Id
    @JsonView(Views.Internal.class)
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_registration_id")
    @SequenceGenerator(name = "seq_registration_id", sequenceName = "seq_registration_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Builder.Default
    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private RegistrationStatus status = RegistrationStatus.CONCEPT;

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
    @JsonProperty("telPhone")
    @Column(name = "tel_phone", length = 20, nullable = false)
    private String telPhone;

//    @NotNull
//    @AssertTrue
//    @JsonProperty("consentGDPR")
//    @Column(name = "consent_gdpr", nullable = false)
//    private Boolean consentGDPR;
//
//    @NotNull
//    @JsonProperty("consentPhoto")
//    @Column(name = "consent_photo")
//    private Boolean consentPhoto;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "event_term_id")
    private EventTerm eventTerm;

    @Builder.Default
    @JsonProperty("people")
    @OrderColumn(name = "ind")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reg_person", joinColumns = @JoinColumn(name = "registration_id"))
    private List<Person> people = new ArrayList<>();

    @Builder.Default
    @JsonProperty("knownPeople")
    @OrderColumn(name = "ind")
    @ElementCollection
    @CollectionTable(name = "reg_known_person", joinColumns = @JoinColumn(name = "registration_id"))
    private List<KnownPerson> knownPeople = new ArrayList<>();

    @JsonProperty("payment")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

    @JsonIgnore
    public boolean hasPerson(Person person) {
        return people.stream().anyMatch(p -> p.isEqual(person));
    }
}
