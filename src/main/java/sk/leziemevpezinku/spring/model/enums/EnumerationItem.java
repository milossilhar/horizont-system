package sk.leziemevpezinku.spring.model.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "reg_enumeration_item")
@NoArgsConstructor
@AllArgsConstructor
public class EnumerationItem {

    @Id
    @JsonIgnore
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_enumeration_item_id")
    @SequenceGenerator(name = "seq_enumeration_item_id", sequenceName = "seq_enumeration_item_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @JsonProperty("enum_name")
    @Enumerated(EnumType.STRING)
    @Column(name = "enum_name", length = 40, nullable = false)
    private EnumerationName name;

    @NotNull
    @JsonProperty("code")
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @NotNull
    @JsonProperty("description")
    @Column(name = "description", length = 150, nullable = false)
    private String value;

    @NotNull
    @JsonProperty("ordering")
    @Column(name = "ordering", nullable = false)
    private Integer ordering;

    @NotNull
    @JsonProperty("visible")
    @Column(name = "visible", nullable = false)
    private Boolean visible;
}
