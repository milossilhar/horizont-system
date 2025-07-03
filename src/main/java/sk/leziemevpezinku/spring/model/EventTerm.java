package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;
import sk.leziemevpezinku.spring.api.enumeration.EventTermRepeatType;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
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

    /** Enumerated: {@link EventTermRepeatType} */
    @ToString.Include
    @Column(name = "repeat_type", length = 10, nullable = false)
    private String repeatType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "day_of_week", length = 10)
    private DayOfWeek dayOfWeek;

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

    @Column(name = "capacity", nullable = false)
    private Long capacity;

    @Builder.Default
    @Column(name = "has_attendance", nullable = false)
    private Boolean hasAttendance = Boolean.FALSE;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @EqualsAndHashCode.Exclude
    @OrderBy(value = "createdAt ASC")
    @OneToMany(mappedBy = "eventTerm", fetch = FetchType.LAZY)
    private Set<Registration> registrations;

    @EqualsAndHashCode.Exclude
    @OrderBy(value = "startAt ASC")
    @OneToMany(mappedBy = "eventTerm", fetch = FetchType.LAZY)
    private Set<Lesson> lessons;
}
