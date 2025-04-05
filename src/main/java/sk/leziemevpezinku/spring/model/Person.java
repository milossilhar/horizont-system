package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import sk.leziemevpezinku.spring.model.annotation.validation.AccentedName;
import sk.leziemevpezinku.spring.model.annotation.validation.Enumeration;
import sk.leziemevpezinku.spring.model.annotation.validation.FreeText;
import sk.leziemevpezinku.spring.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @NotNull
    @AccentedName
    @JsonProperty("name")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @AccentedName
    @JsonProperty("surname")
    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    @Past
    @NotNull
    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @FreeText
    @Size(max = 1000)
    @JsonProperty("health_notes")
    @Column(name = "health_notes", length = 1000)
    private String healthNotes;

    @FreeText
    @Size(max = 1000)
    @JsonProperty("food_allergy_notes")
    @Column(name = "food_allergy_notes", length = 1000)
    private String foodAllergyNotes;

    // enum - REG_E_SHIRT_SIZE
    @Enumeration
    @JsonProperty("shirt_size")
    @Column(name = "shirt_size", length = 10)
    private String shirtSize;

    @JsonProperty("is_independent")
    @Column(name = "is_independent", nullable = false)
    private Boolean isIndependent;

    @ManyToOne
    @JsonBackReference
    @JsonProperty("parent")
    @JoinColumn(name = "parent_id")
    private User parent;

    @JsonProperty("registrations")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private List<Registration> registrations = new ArrayList<>();

    @JsonGetter("fullname")
    public String getFullname() {
        return this.name + " " + this.surname;
    }

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
