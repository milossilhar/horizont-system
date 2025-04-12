package sk.leziemevpezinku.spring.model;

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
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Person {

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
    @JsonProperty("dateOfBirth")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @FreeText
    @Size(max = 1000)
    @JsonProperty("healthNotes")
    @Column(name = "health_notes", length = 1000)
    private String healthNotes;

    @FreeText
    @Size(max = 1000)
    @JsonProperty("foodAllergyNotes")
    @Column(name = "food_allergy_notes", length = 1000)
    private String foodAllergyNotes;

    // enum - REG_E_SHIRT_SIZE
    @Enumeration
    @JsonProperty("shirtSize")
    @Column(name = "shirt_size", length = 10)
    private String shirtSize;

    @JsonProperty("isIndependent")
    @Column(name = "is_independent", nullable = false)
    private Boolean isIndependent;

    @JsonGetter("fullName")
    public String getFullName() {
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
