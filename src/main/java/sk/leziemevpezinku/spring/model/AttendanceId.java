package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class AttendanceId implements Serializable {

    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @Column(name = "person_id", nullable = false)
    private Long personId;
}
