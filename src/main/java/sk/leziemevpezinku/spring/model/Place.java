package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_place")
public class Place extends AuditedEntityBase {

    @Id
    @ToString.Include
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_place_id")
    @SequenceGenerator(name = "seq_place_id", sequenceName = "seq_place_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @ToString.Include
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @ToString.Include
    @Column(name = "latitude", precision = 12, scale = 8, nullable = false)
    private BigDecimal latitude;

    @ToString.Include
    @Column(name = "longitude", precision = 12, scale = 8, nullable = false)
    private BigDecimal longitude;

    @Column(name = "training_price")
    private BigDecimal training_price;
}
