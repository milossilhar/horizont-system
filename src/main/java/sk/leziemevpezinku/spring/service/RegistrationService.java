package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Registration;

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
}
