package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import sk.leziemevpezinku.spring.model.base.AuditedCreationEntityBase;

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

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "confirmed_by_user")
    private Boolean confirmedByUser;

    @Column(name = "confirmed_by_system")
    private Boolean confirmedBySystem;

    @Column(name = "attended")
    private Boolean attended;
}
