package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;
import sk.leziemevpezinku.spring.model.enums.AttendanceStatus;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_attendance")
public class Attendance extends AuditedEntityBase {

    @ToString.Include
    @EmbeddedId
    private AttendanceId id;

    @EqualsAndHashCode.Exclude
    @MapsId("lessonId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    @EqualsAndHashCode.Exclude
    @MapsId("personId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    /** Enumerated: {@link AttendanceStatus} */
    @ToString.Include
    @Column(name = "status", length = 10, nullable = false)
    private String status;
}
