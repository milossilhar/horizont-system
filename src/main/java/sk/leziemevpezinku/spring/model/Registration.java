package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_registration_id")
    @SequenceGenerator(name = "seq_registration_id", sequenceName = "seq_registration_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @JsonProperty("transaction_id")
    @Column(name = "transaction_id", length = 40, nullable = false, updatable = false)
    private String transactionId;

    @JsonProperty("confirmed_by")
    @Column(name = "confirmed_by")
    private String confirmedBy;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "event_term_id")
    private EventTerm eventTerm;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "person_id")
    private Person person;
}
