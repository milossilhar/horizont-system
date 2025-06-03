package sk.leziemevpezinku.spring.mustache;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailPerson {
    private String name;
    private String surname;
    private String birthDate;
    private String shirtSize;
    private String healthNotes;
    private String foodAllergyNotes;
}
