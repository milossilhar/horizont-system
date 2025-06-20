package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AttendanceId implements Serializable {

    @ToString.Include
    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @ToString.Include
    @Column(name = "person_id", nullable = false)
    private Long personId;
}
