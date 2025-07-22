package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import sk.leziemevpezinku.spring.api.enumeration.EventType;
import sk.leziemevpezinku.spring.model.base.UuidAuditedEntity;
import sk.leziemevpezinku.spring.api.enumeration.EventStatus;

import java.time.LocalDateTime;
import java.util.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg_event")
public class Event extends UuidAuditedEntity {

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

    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", length = 20, nullable = false)
    private EventType eventType;

    @Column(name = "image_url", length = 100)
    private String imageUrl;

    @Column(name = "registration_starts", nullable = false)
    private LocalDateTime registrationStarts;

    @Column(name = "registration_ends", nullable = false)
    private LocalDateTime registrationEnds;

    @ToString.Include
    @Column(name = "locked")
    private LocalDateTime locked;

    @Builder.Default
    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private EventStatus status = EventStatus.DRAFT;

    /** Enumerated: REG_PLACE */
    @ToString.Include
    @Column(name = "place_code", length = 10, nullable = false)
    private String placeCode;

    /** Enumerated: REG_PERIOD */
    @ToString.Include
    @Column(name = "period_code", length = 10)
    private String periodCode;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ElementCollection
    @OrderColumn(name = "ind")
    @CollectionTable(name = "reg_event_condition", joinColumns = @JoinColumn(name = "event_id"))
    private List<EventCondition> conditions = new ArrayList<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private Set<EventTerm> terms = new HashSet<>();
}
