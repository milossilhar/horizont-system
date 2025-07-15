package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Payment;
import sk.leziemevpezinku.spring.model.Registration;

import java.util.List;
import java.util.function.BiConsumer;

public interface RegistrationService {

    /**
     * Gets registration by ID.
     * @param id id
     * @return registration
     */
    Registration getById(Long id);

    /**
     * Gets registration by payment ID.
     * @param paymentId payment id
     * @return registration
     */
    Registration getByPaymentId(Long paymentId);

    /**
     * Creates registration for user.
     * @param eventTermId event term id
     * @param registration object with registration info
     * @return created reservation
     */
    Registration createRegistration(Long eventTermId, Registration registration);

    /**
     * Confirms registration using given JWT token from email.
     * @param jwtToken jwt token for registration
     * @return confirmed registration
     */
    Registration confirmRegistration(String jwtToken);

    /**
     * Calculates price for registration based on number of people registered.
     * @param eventTermId event term id
     * @param userEmail user email
     * @param numberOfPeople number of people to be registered
     * @return generated payment with all the info
     */
    Payment calculatePriceForRegistration(Long eventTermId, String userEmail, Long numberOfPeople);

    /**
     * Updates flag on registration in new transaction
     * @param id registration id
     * @param invoker setter on registration
     */
    void updateFlagNewTx(Long id, BiConsumer<Registration, Boolean> invoker);

    /**
     * Finds all registrations where payment-info email was not sent.
     * @param batchSize max size of the result
     * @return list of found registrations
     */
    List<Registration> findForPaymentInfo(Long batchSize);

    /**
     * Finds all registrations where event-detail email was not sent.
     * @param batchSize max size of the result
     * @return list of found registrations
     */
    List<Registration> findForEventDetail(Long batchSize);
}
