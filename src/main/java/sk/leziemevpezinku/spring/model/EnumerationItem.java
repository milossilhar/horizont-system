package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;

@Entity
@Table(name = "reg_enumeration_item")
public class EnumerationItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_enumeration_item_id")
    @SequenceGenerator(name = "seq_enumeration_item_id", sequenceName = "seq_enumeration_item_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "enum_name", length = 40, nullable = false)
    private EnumerationName name;

    @Column(name = "enum_code", length = 10, nullable = false)
    private String code;

    @Column(name = "enum_value", length = 150, nullable = false)
    private String value;

    @Column(name = "visible", nullable = false)
    private Boolean visible;
}
