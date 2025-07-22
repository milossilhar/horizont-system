package sk.leziemevpezinku.spring.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class UuidAuditedEntity extends AuditedEntity {

    @Column(name = "uuid", length = 40, nullable = false, unique = true, updatable = false)
    private String uuid;

    @Override
    public void preSave() {
        super.preSave();
        this.uuid = UUID.randomUUID().toString();
    }
}
