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
public class AttendanceTrainerId implements Serializable {

    @ToString.Include
    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @ToString.Include
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
