package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person_id")
    @SequenceGenerator(name = "seq_person_id", sequenceName = "seq_person_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "person_name", length = 200, nullable = false)
    private String name;

    @Column(name = "person_surname", length = 200, nullable = false)
    private String surname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "health_notes", length = 1000)
    private String healthNotes;

    @Column(name = "food_allergy_notes", length = 1000)
    private String foodAllergyNotes;

    // enum - REG_E_SHIRT_SIZE
    @Column(name = "shirt_size", length = 10)
    private String shirtSize;

    @Column(name = "is_independent", nullable = false)
    private Boolean isIndependent = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private List<Registration> registrations;
}
