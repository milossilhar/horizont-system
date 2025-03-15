package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_event")
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_id")
    @SequenceGenerator(name = "seq_event_id", sequenceName = "seq_event_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "event_name", nullable = false, length = 200)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 20)
    private EventType eventType;

    @Column(name = "reg_start_at", nullable = false)
    private LocalDateTime regStartAt;

    @Column(name = "reg_end_at", nullable = false)
    private LocalDateTime regEndAt;

    @OrderColumn(name = "ind")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reg_event_condition", joinColumns = @JoinColumn(name = "event_id"))
    private List<EventRegistrationCondition> conditions;

    @Column(name = "event_start_at", nullable = false)
    private LocalDateTime eventStartAt;

    @Column(name = "event_end_at", nullable = false)
    private LocalDateTime eventEndAt;

    @Column(name = "begin_at")
    private LocalTime beginAt;

    @Column(name = "end_at")
    private LocalTime endAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event")
    private List<Registration> registrations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    private List<Lesson> lessons;
}
