package sk.leziemevpezinku.spring.mustache;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailRegistrationConfirmKnownPerson {
    private String name;
    private String surname;
    private String relation;
}
