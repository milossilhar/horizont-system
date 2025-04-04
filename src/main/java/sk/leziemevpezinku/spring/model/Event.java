package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sk.leziemevpezinku.spring.model.base.UidAuditedEntityBase;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_event")
public class Event extends UidAuditedEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_id")
    @SequenceGenerator(name = "seq_event_id", sequenceName = "seq_event_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @JsonProperty("event_name")
    @Column(name = "event_name", nullable = false, length = 200)
    private String name;

    @NotNull
    @JsonProperty("event_type")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 20)
    private EventType eventType;

    @NotNull
    @JsonProperty("registration_start")
    @Column(name = "reg_start_at", nullable = false)
    private LocalDateTime regStartAt;

    @NotNull
    @JsonProperty("registration_end")
    @Column(name = "reg_end_at", nullable = false)
    private LocalDateTime regEndAt;

    @OrderColumn(name = "ind")
    @JsonProperty("conditions")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reg_event_condition", joinColumns = @JoinColumn(name = "event_id"))
    private List<EventCondition> conditions;

    @OrderColumn(name = "ind")
    @JsonProperty("discounts")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reg_event_discount", joinColumns = @JoinColumn(name = "event_id"))
    private List<EventDiscount> discounts;

    @JsonProperty("terms")
    @OneToMany(mappedBy = "event")
    private List<EventTerm> terms;
}
