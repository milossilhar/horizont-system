package sk.leziemevpezinku.spring.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class UUIDAuditedEntityBase extends AuditedEntityBase {

    @Column(name = "uuid", length = 40, nullable = false, unique = true, updatable = false)
    private String uuid;

    @Override
    public void preSave() {
        super.preSave();
        this.uuid = UUID.randomUUID().toString();
    }
}
