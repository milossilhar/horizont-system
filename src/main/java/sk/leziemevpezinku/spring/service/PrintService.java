package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Registration;

import java.math.BigDecimal;

public interface PrintService {
    /** Template names. */
    interface Templates {
        interface Emails {
            String REGISTRATION_CONFIRM = "email-registration-confirm";
            String PAYMENT_INFO = "email-payment-info";
            String PAYMENT_CONFIRM = "email-payment-confirm";
            String PAYMENT_COMPLETE_CONFIRM = "email-payment-complete-confirm";
            String EVENT_CAMP_DETAIL = "email-event-camp-detail";
            String EVENT_WEEK_CAMP_DETAIL = "email-camp-detail";
        }
    }

    /**
     * Creates email for registration confirmation with data from registration.
     * @param registration registration for which create email
     * @return html code of an email with substituted values from registration
     */
    String printRegistrationConfirmed(Registration registration);

    /**
     * Creates email for payment information with data
     * @param registration registration for which create email
     * @return html code of an email with substituted values
     */
    String printPaymentInfo(Registration registration);

    /**
     * Generates a payment confirmation email for the given registration.
     *
     * @param registration the registration associated with the payment
     * @param deposit indicates whether the payment confirmed is a deposit (true) or the full payment (false)
     * @param amountPaid the amount that has been paid
     * @return a string containing the generated email content with substituted values for payment confirmation
     */
    String printPaymentConfirm(Registration registration, Boolean deposit, BigDecimal amountPaid);

    /**
     * Generates a detailed event information email using data from the given registration.
     *
     * @param registration the registration containing data for creating the event detail email
     * @return a string containing the generated email with substituted values from the registration
     */
    String printEventDetail(Registration registration);
}
