package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_enumeration_item")
public class EnumerationItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_enumeration_item_id")
    @SequenceGenerator(name = "seq_enumeration_item_id", sequenceName = "seq_enumeration_item_id", initialValue = 1, allocationSize = 1)
    private Long id;

    /** Enumerated :: {@link EnumerationName} */
    @ToString.Include
    @Column(name = "enum_name", length = 40, nullable = false)
    private String name;

    @ToString.Include
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @ToString.Include
    @Column(name = "description", length = 150, nullable = false)
    private String description;

    @Column(name = "ordering", nullable = false)
    private Integer ordering;

    @Column(name = "visible", nullable = false)
    private Boolean visible;
}
