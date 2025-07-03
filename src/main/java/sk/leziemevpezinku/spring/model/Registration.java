package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import sk.leziemevpezinku.spring.model.base.CreatedAtEntityBase;
import sk.leziemevpezinku.spring.model.enums.RegistrationStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_registration")
public class Registration extends CreatedAtEntityBase {

    @Id
    @ToString.Include
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_registration_id")
    @SequenceGenerator(name = "seq_registration_id", sequenceName = "seq_registration_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @ToString.Include
    @Column(name = "uuid", length = 40, nullable = false, updatable = false)
    private String uuid;

    /** Enumerated: {@link RegistrationStatus} */
    @Builder.Default
    @ToString.Include
    @Column(name = "status", length = 10, nullable = false)
    private String status = RegistrationStatus.CONCEPT.name();

    @Column(name = "name", length = 50, nullable = false)
    private String parentName;

    @Column(name = "surname", length = 50, nullable = false)
    private String parentSurname;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "tel_phone", length = 20, nullable = false)
    private String telPhone;

    @Column(name = "payment_scheme", length = 10)
    private String paymentScheme;

    @Column(name = "consent_gdpr", nullable = false)
    private Boolean consentGDPR;

    @Column(name = "consent_photo")
    private Boolean consentPhoto;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_term_id", nullable = false)
    private EventTerm eventTerm;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @OrderColumn(name = "ind")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reg_known_person", joinColumns = @JoinColumn(name = "registration_id"))
    private List<KnownPerson> knownPeople = new ArrayList<>();
}
