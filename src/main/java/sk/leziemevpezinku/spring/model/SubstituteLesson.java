package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.leziemevpezinku.spring.model.base.CreatedAtEntityBase;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Embeddable
public class SubstituteLesson extends CreatedAtEntityBase {

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "lesson_id")
    private Long lessonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", insertable = false, updatable = false)
    private Lesson lesson;
}
