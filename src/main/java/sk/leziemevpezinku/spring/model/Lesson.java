package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sk.leziemevpezinku.spring.model.base.AuditedEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_lesson")
public class Lesson extends AuditedEntity {

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

    /** Enumerated: REG_PLACE */
    @ToString.Include
    @Column(name = "place_code", length = 10, nullable = false)
    private String placeCode;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_term_id", nullable = false)
    private EventTerm eventTerm;
}
