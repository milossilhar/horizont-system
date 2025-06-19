package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;
import sk.leziemevpezinku.spring.model.enums.AttendanceStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reg_attendance")
public class Attendance extends AuditedEntityBase {

    @EmbeddedId
    private AttendanceId id;

    @MapsId("lessonId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    @MapsId("personId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    /** Enumerated :: {@link AttendanceStatus} */
    @Column(name = "status", length = 10, nullable = false)
    private String status;
}
