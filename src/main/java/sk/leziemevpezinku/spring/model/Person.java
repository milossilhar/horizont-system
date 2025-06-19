package sk.leziemevpezinku.spring.model;

import jakarta.persistence.*;
import lombok.*;
import sk.leziemevpezinku.spring.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@Entity
@Table(name = "reg_person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person_id")
    @SequenceGenerator(name = "seq_person_id", sequenceName = "seq_person_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "health_notes", length = 1000)
    private String healthNotes;

    @Column(name = "food_allergy_notes", length = 1000)
    private String foodAllergyNotes;

    // enum - REG_E_SHIRT_SIZE
    @Column(name = "shirt_size", length = 10)
    private String shirtSize;

    @Column(name = "is_independent", nullable = false)
    private Boolean isIndependent;

    @ElementCollection
    @OrderColumn(name = "ind")
    @CollectionTable(name = "reg_substitute_lesson", joinColumns = @JoinColumn(name = "person_id"))
    private List<SubstituteLesson> substituteLessons;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isEqual(Person person) {
        if (person == null) return false;

        String normalizedName = StringUtils.normalize(name);
        String normalizedPersonName = StringUtils.normalize(person.name);

        String normalizedSurname = StringUtils.normalize(surname);
        String normalizedPersonSurname = StringUtils.normalize(person.surname);

        return Objects.equals(normalizedName, normalizedPersonName) &&
                Objects.equals(normalizedSurname, normalizedPersonSurname) &&
                (dateOfBirth == null || dateOfBirth.isEqual(person.dateOfBirth));
    }
}
