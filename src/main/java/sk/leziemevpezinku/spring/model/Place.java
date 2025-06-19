package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reg_place")
public class Place extends AuditedEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_period_id")
    @SequenceGenerator(name = "seq_period_id", sequenceName = "seq_period_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "latitude", precision = 12, scale = 8, nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 12, scale = 8, nullable = false)
    private BigDecimal longitude;

    @Column(name = "training_price")
    private BigDecimal training_price;
}
