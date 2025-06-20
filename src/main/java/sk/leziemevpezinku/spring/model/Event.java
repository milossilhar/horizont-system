package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import sk.leziemevpezinku.spring.model.base.UUIDAuditedEntityBase;
import sk.leziemevpezinku.spring.model.enums.EventType;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_event")
public class Event extends UUIDAuditedEntityBase {

    @Id
    @ToString.Include
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_id")
    @SequenceGenerator(name = "seq_event_id", sequenceName = "seq_event_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @ToString.Include
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "details", nullable = false, length = 2000)
    private String details;

    /** Enumerated :: {@link EventType} */
    @ToString.Include
    @Column(name = "event_type", length = 20, nullable = false)
    private String eventType;

    @Column(name = "image_url", length = 100)
    private String imageUrl;

    @Column(name = "reg_start_at", nullable = false)
    private LocalDateTime regStartAt;

    @Column(name = "reg_end_at", nullable = false)
    private LocalDateTime regEndAt;

    @ToString.Include
    @Column(name = "locked")
    private Boolean locked;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "period_id", nullable = false)
    private Period period;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @OrderColumn(name = "ind")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reg_event_condition", joinColumns = @JoinColumn(name = "event_id"))
    private List<EventCondition> conditions = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private Set<EventTerm> terms = new HashSet<>();
}
