package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_lesson")
public class Lesson {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lesson_id")
    @SequenceGenerator(name = "seq_lesson_id", sequenceName = "seq_lesson_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled;

    @Column(name = "takes_place", length = 200)
    private String takesPlace;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reg_lesson_attendance",
            joinColumns = @JoinColumn(name = "lesson_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false)
    )
    private Set<Person> attendance;
}
