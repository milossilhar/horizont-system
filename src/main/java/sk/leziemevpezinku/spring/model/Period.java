package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_period")
public class Period extends AuditedEntityBase {

    @Id
    @Access(AccessType.PROPERTY)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_period_id")
    @SequenceGenerator(name = "seq_period_id", sequenceName = "seq_period_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @ToString.Include
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "period", fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<>();
}
