package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reg_lesson")
public class Lesson extends AuditedEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lesson_id")
    @SequenceGenerator(name = "seq_lesson_id", sequenceName = "seq_lesson_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(name = "expected_trainers")
    private List<String> expectedTrainers = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_term_id", nullable = false)
    private EventTerm eventTerm;
}
