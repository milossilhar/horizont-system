package sk.leziemevpezinku.spring.model.enums;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "reg_enumeration_item")
public class EnumerationItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_enumeration_item_id")
    @SequenceGenerator(name = "seq_enumeration_item_id", sequenceName = "seq_enumeration_item_id", initialValue = 1, allocationSize = 1)
    private Long id;

    /** Enumerated :: {@link EnumerationName} */
    @Column(name = "enum_name", length = 40, nullable = false)
    private String name;

    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "description", length = 150, nullable = false)
    private String description;

    @Column(name = "ordering", nullable = false)
    private Integer ordering;

    @Column(name = "visible", nullable = false)
    private Boolean visible;
}
