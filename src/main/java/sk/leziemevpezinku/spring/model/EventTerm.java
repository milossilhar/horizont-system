package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
    private LocalDateTime eventStartAt;

    @JsonProperty("end_at")
    @Column(name = "end_at", nullable = false)
    private LocalDateTime eventEndAt;

    @ManyToOne
    @JsonBackReference
    @JsonProperty("event")
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @JsonProperty("registrations")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "eventTerm")
    private List<Registration> registrations;
}
