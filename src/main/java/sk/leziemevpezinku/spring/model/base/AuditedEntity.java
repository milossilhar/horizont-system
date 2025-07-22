package sk.leziemevpezinku.spring.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sk.leziemevpezinku.spring.util.SecurityUtils;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class AuditedEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 50, updatable = false)
    private String createdBy;

    @Column(name = "modified_at", insertable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by", length = 50, insertable = false)
    private String modifiedBy;

    @PrePersist
    public void preSave() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = SecurityUtils.getCurrentUser(50);
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = SecurityUtils.getCurrentUser(50);
    }
}
