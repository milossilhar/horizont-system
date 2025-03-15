package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import sk.leziemevpezinku.spring.model.base.AuditedCreationEntityBase;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SubstituteLesson extends AuditedCreationEntityBase {

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "expired", nullable = false)
    private Boolean expired;

    @Column(name = "used_by_lesson")
    private Long usedByLesson;
}
