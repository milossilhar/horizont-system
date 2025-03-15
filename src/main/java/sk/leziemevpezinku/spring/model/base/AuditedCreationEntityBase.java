package sk.leziemevpezinku.spring.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class AuditedCreationEntityBase {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void preSave() {
        this.createdAt = LocalDateTime.now();
    }
}
