package sk.leziemevpezinku.spring.mustache;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmailPaymentInfo {
    private String name;
    private String surname;
    private String eventName;
    private String startDateTime;
    private String endDateTime;

    private String iban;
    private String variableSymbol;
    private String note;
    private String paymentValue;
    private String payBySquareURL;
    private String depositPerChild;

    private List<EmailPerson> people;

    public boolean getHasPayBySquare() {
        return this.payBySquareURL != null;
    }

    public int getPeopleLength() {
        return this.people != null ? this.people.size() : 0;
    }
}
