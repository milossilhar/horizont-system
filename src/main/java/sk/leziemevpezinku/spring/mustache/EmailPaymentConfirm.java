package sk.leziemevpezinku.spring.mustache;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmailPaymentConfirm {

    private String name;
    private String surname;
    private String eventName;
    private String eventStartDate;

    private String paymentSubject;
    private String paymentSubjectCaps;

    private String paymentValue;

    private String variableSymbol;
    private String remainingAmount;
    private String depositAmount;
    private String discountAmount;
    private String totalAmount;

    private List<EmailPerson> people;

    public String getPeopleLength() {
        return String.valueOf(people.size());
    }

    public boolean getHasDiscount() {
        return this.discountAmount != null && !this.discountAmount.isEmpty();
    }
}
