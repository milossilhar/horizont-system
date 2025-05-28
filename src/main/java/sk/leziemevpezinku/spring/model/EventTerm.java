package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import sk.leziemevpezinku.spring.repo.model.EventTermCapacity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_event_term")
public class EventTerm implements Comparable<EventTerm> {

    @Id
    @JsonView(Views.Public.class)
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_term_id")
    @SequenceGenerator(name = "seq_event_term_id", sequenceName = "seq_event_term_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @JsonView(Views.Public.class)
    @JsonProperty("startAt")
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @JsonView(Views.Public.class)
    @JsonProperty("endAt")
    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @NotNull
    @Positive
    @JsonView(Views.Public.class)
    @JsonProperty("capacity")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @Positive
    @JsonView(Views.Public.class)
    @JsonProperty("deposit")
    @Column(name = "deposit", nullable = false)
    private BigDecimal deposit;

    @NotNull
    @Positive
    @JsonView(Views.Public.class)
    @JsonProperty("price")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @JsonView(Views.EventTerm.class)
    @JsonProperty("event")
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @JsonView(Views.EventTerm.class)
    @JsonProperty("registrations")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventTerm")
    private List<Registration> registrations;

    @Builder.Default
    @JsonView(Views.Internal.class)
    @JsonProperty("currentCapacities")
    @Transient
    private List<EventTermCapacity> currentCapacities = new ArrayList<>();

    @Override
    public int compareTo(EventTerm eventTerm) {
        return startAt.compareTo(eventTerm.startAt);
    }
}
