package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reg_period")
public class Period extends AuditedEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_period_id")
    @SequenceGenerator(name = "seq_period_id", sequenceName = "seq_period_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @OneToMany(mappedBy = "period", fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<>();
}
