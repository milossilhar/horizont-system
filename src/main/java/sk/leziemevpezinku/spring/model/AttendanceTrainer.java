package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reg_attendance_trainer")
public class AttendanceTrainer extends AuditedEntityBase {

    @EmbeddedId
    private AttendanceTrainerId id;

    @MapsId("lessonId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;
}
