package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;
import sk.leziemevpezinku.spring.api.enumeration.EventConditionTypeOption;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "reg_enumeration_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"enum_name", "code"})
)
public class EnumerationItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_enumeration_item_id")
    @SequenceGenerator(name = "seq_enumeration_item_id", sequenceName = "seq_enumeration_item_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "enum_name", length = 40, nullable = false)
    private EnumerationName enumName;

    @ToString.Include
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @ToString.Include
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @ToString.Include
    @Column(name = "ordering", nullable = false)
    private Integer ordering;

    @ToString.Include
    @Column(name = "hidden")
    private Boolean hidden;

    // REG_PLACE
    @Column(name = "latitude", precision = 18, scale = 15)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 18, scale = 15)
    private BigDecimal longitude;

    // REG_EVENT_CONDITION_TYPE
    @Enumerated(EnumType.STRING)
    @Column(name = "condition_option", length = 10)
    private EventConditionTypeOption conditionOption;
}
