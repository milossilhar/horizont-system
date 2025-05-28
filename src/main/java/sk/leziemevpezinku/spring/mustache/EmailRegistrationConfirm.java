package sk.leziemevpezinku.spring.mustache;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmailRegistrationConfirm {
    private String name;
    private String surname;
    private String status;
    private String email;
    private String telPhone;
    private String createdAt;
    private String eventName;
    private String startDateTime;
    private String endDateTime;
    private String location;
    private Long peopleLength;
    private boolean consentGDPR;
    private boolean consentPhoto;
    private List<EmailRegistrationConfirmPerson> people;
    private List<EmailRegistrationConfirmKnownPerson> knownPeople;

    public boolean getHasKnownPeople() {
        return this.knownPeople != null && !this.knownPeople.isEmpty();
    }
}
