package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Registration;

import java.math.BigDecimal;

public interface NotificationService {

    /**
     * Sends notification about created registration.
     * @param registration registration to send notification for
     */
    void sendRegistrationCreatedNotification(Registration registration);

    /**
     * Sends notification about confirmed registration.
     * @param registration registration to send notification for
     */
    void sendRegistrationConfirmedNotification(Registration registration);

    /**
     * Sends notification about a payment
     * @param registration registration to send notification for
     */
    void sendPaymentInformationNotification(Registration registration);

    /**
     * Sends a payment confirmation notification.
     *
     * @param registration the registration for which the payment confirmation notification is sent
     * @param deposit true if the payment confirmation is for a deposit, false if it is for the full payment
     * @param amountPaid the amount that has been paid
     */
    void sendPaymentConfirmationNotification(Registration registration, Boolean deposit, BigDecimal amountPaid);

    /**
     * Sends a detailed event notification using information from the given registration.
     *
     * @param registration the registration containing data for sending the event detail notification
     */
    void sendEventDetailNotification(Registration registration);
}
