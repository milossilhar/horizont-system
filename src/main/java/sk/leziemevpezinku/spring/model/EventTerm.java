package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SortComparator;
import org.hibernate.type.SqlTypes;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;
import sk.leziemevpezinku.spring.model.compare.AuditedCreatedAtComparator;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_event_term")
public class EventTerm extends AuditedEntityBase {

    @Id
    @ToString.Include
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_term_id")
    @SequenceGenerator(name = "seq_event_term_id", sequenceName = "seq_event_term_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @ToString.Include
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ToString.Include
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @ToString.Include
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "number_of_lessons")
    private Long numberOfLessons;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(name = "expected_trainers")
    private List<String> expectedTrainers = new ArrayList<>();

    @Column(name = "deposit")
    private BigDecimal deposit;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "has_attendance")
    private Boolean hasAttendance;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @EqualsAndHashCode.Exclude
    @SortComparator(AuditedCreatedAtComparator.class)
    @OneToMany(mappedBy = "eventTerm", fetch = FetchType.LAZY)
    private SortedSet<Registration> registrations;
}
