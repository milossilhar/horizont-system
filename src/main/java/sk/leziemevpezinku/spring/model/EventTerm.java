package sk.leziemevpezinku.spring.model;

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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_term_id")
    @SequenceGenerator(name = "seq_event_term_id", sequenceName = "seq_event_term_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime eventStartAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime eventEndAt;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "eventTerm")
    private List<Registration> registrations;
}
