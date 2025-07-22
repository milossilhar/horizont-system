package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sk.leziemevpezinku.spring.model.base.CreatedAtEntity;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SubstituteLesson extends CreatedAtEntity {

    @ToString.Include
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @ToString.Include
    @Column(name = "lesson_id")
    private Long lessonId;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", insertable = false, updatable = false)
    private Lesson lesson;
}
