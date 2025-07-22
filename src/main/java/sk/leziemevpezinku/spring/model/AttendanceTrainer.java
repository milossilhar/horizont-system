package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sk.leziemevpezinku.spring.model.base.AuditedEntity;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_attendance_trainer")
public class AttendanceTrainer extends AuditedEntity {

    @ToString.Include
    @EmbeddedId
    private AttendanceTrainerId id;

    @EqualsAndHashCode.Exclude
    @MapsId("lessonId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    @EqualsAndHashCode.Exclude
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @ToString.Include
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;
}
