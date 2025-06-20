package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_lesson")
public class Lesson extends AuditedEntityBase {

    @Id
    @ToString.Include
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lesson_id")
    @SequenceGenerator(name = "seq_lesson_id", sequenceName = "seq_lesson_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @ToString.Include
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @ToString.Include
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(name = "expected_trainers")
    private List<String> expectedTrainers = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_term_id", nullable = false)
    private EventTerm eventTerm;
}
