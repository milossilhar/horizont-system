package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Payment;
import sk.leziemevpezinku.spring.model.Registration;

import java.util.function.BiConsumer;

public interface RegistrationService {

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
     *
     * @param eventTermId
     * @param userEmail
     * @param numberOfPeople
     * @return
     */
    Payment calculatePriceForRegistration(Long eventTermId, String userEmail, Long numberOfPeople);

    /**
     * Updates flag on registration in new transaction
     * @param id registration id
     * @param invoker setter on registration
     */
    void updateFlag(Long id, BiConsumer<Registration, Boolean> invoker);
}
