package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person_id")
    @SequenceGenerator(name = "seq_person_id", sequenceName = "seq_person_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @JsonProperty("name")
    @Column(name = "person_name", length = 200, nullable = false)
    private String name;

    @JsonProperty("surname")
    @Column(name = "person_surname", length = 200, nullable = false)
    private String surname;

    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("health_notes")
    @Column(name = "health_notes", length = 1000)
    private String healthNotes;

    @JsonProperty("food_allergy_notes")
    @Column(name = "food_allergy_notes", length = 1000)
    private String foodAllergyNotes;

    // enum - REG_E_SHIRT_SIZE
    @JsonProperty("shirt_size")
    @Column(name = "shirt_size", length = 10)
    private String shirtSize;

    @JsonProperty("is_independent")
    @Column(name = "is_independent", nullable = false)
    private Boolean isIndependent = Boolean.FALSE;

    @ManyToOne
    @JsonBackReference
    @JsonProperty("parent")
    @JoinColumn(name = "parent_id")
    private User parent;

    @JsonProperty("registrations")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private List<Registration> registrations = new ArrayList<>();
}
