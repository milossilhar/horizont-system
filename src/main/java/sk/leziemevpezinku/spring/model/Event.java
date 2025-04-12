package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import sk.leziemevpezinku.spring.model.base.UidAuditedEntityBase;
import sk.leziemevpezinku.spring.model.enums.EventType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_event")
public class Event extends UidAuditedEntityBase {

    @Id
    @JsonView(Views.Internal.class)
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_id")
    @SequenceGenerator(name = "seq_event_id", sequenceName = "seq_event_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @JsonProperty("name")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @NotNull
    @JsonProperty("details")
    @Column(name = "details", nullable = false, length = 2000)
    private String details;

    @NotNull
    @JsonProperty("place")
    @Column(name = "place", nullable = false, length = 300)
    private String place;

    @NotNull
    @JsonProperty("eventType")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 20)
    private EventType eventType;

    @NotNull
    @JsonProperty("regStartAt")
    @Column(name = "reg_start_at", nullable = false)
    private LocalDateTime regStartAt;

    @NotNull
    @JsonProperty("regEndAt")
    @Column(name = "reg_end_at", nullable = false)
    private LocalDateTime regEndAt;

    @JsonProperty("discounts")
    @OrderColumn(name = "ind")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reg_event_discount", joinColumns = @JoinColumn(name = "event_id"))
    private List<EventDiscount> discounts;

    @JsonProperty("terms")
    @OneToMany(mappedBy = "event")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<EventTerm> terms;
}
