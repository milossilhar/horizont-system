package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Registration;

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
     * Sends notification about confirmed payment
     * @param registration registration to send notification for
     */
    void sendPaymentConfirmationNotification(Registration registration);
}
