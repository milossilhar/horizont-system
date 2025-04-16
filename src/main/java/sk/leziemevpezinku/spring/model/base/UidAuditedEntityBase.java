package sk.leziemevpezinku.spring.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import sk.leziemevpezinku.spring.model.Views;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class UidAuditedEntityBase extends AuditedCreationEntityBase {

    @JsonView(Views.Public.class)
    @JsonProperty("uuid")
    @Column(name = "uuid", length = 40, nullable = false, unique = true, updatable = false)
    private String uuid;

    @Override
    public void preSave() {
        super.preSave();
        this.uuid = UUID.randomUUID().toString();
    }
}
