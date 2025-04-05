package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_event_term")
public class EventTerm {

    @Id
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_term_id")
    @SequenceGenerator(name = "seq_event_term_id", sequenceName = "seq_event_term_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @JsonProperty("start_at")
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @JsonProperty("end_at")
    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @NotNull
    @Positive
    @JsonProperty("capacity")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @ManyToOne
    @JsonBackReference
    @JsonProperty("event")
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @JsonProperty("registrations")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventTerm")
    private List<Registration> registrations;
}
