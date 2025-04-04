package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import sk.leziemevpezinku.spring.model.base.AuditedCreationEntityBase;
import sk.leziemevpezinku.spring.model.base.UidAuditedEntityBase;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_registration")
public class Registration extends AuditedCreationEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_registration_id")
    @SequenceGenerator(name = "seq_registration_id", sequenceName = "seq_registration_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "uuid", length = 40, nullable = false, unique = true, updatable = false)
    private String uuid;

    @Column(name = "confirmed_by")
    private String confirmedBy;

    @ManyToOne
    @JoinColumn(name = "event_term_id")
    private EventTerm eventTerm;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
