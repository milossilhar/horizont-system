package sk.leziemevpezinku.spring.mustache;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmailEventDetail {
    private String eventName;
    private String name;
    private String surname;
    private String startDate;
    private String endDate;

    private String discountValue;

    private String iban;
    private String variableSymbol;
    private String note;
    private String paymentValue;
    private String payBySquareURL;

    private List<EmailPerson> people;
    private List<EmailKnownPerson> knownPeople;

    public boolean getHasDiscount() {
        return this.discountValue != null && !this.discountValue.isEmpty();
    }

    public boolean getHasKnownPeople() {
        return this.knownPeople != null && !this.knownPeople.isEmpty();
    }

    public boolean getHasPayBySquare() {
        return this.payBySquareURL != null;
    }

    public int getPeopleLength() {
        return this.people != null ? this.people.size() : 0;
    }
    public String getPeopleLengthValue() {
        if (this.people == null) return "";
        if (this.people.size() == 1) {
            return "1 dieťa";
        }
        return this.people.size() + " deti";
    }
}
