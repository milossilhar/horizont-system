package sk.leziemevpezinku.spring.model.enums;

import jakarta.persistence.*;

@Entity
@Table(name = "reg_enumeration_item")
public class EnumerationItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_enumeration_item_id")
    @SequenceGenerator(name = "seq_enumeration_item_id", sequenceName = "seq_enumeration_item_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "enum_name", length = 40)
    private EnumerationName name;

    @Column(name = "enum_code", length = 30)
    private String code;

    @Column(name = "enum_value", length = 150)
    private String value;

    @Column(name = "visible")
    private Boolean visible;
}
