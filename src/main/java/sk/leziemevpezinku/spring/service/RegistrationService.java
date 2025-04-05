package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.model.User;

import java.util.List;

public interface RegistrationService {

    /**
     * Creates registration for user.
     * @param eventTermId event term id
     * @param user object with user info
     * @return list of created reservations
     */
    List<Registration> createRegistration(Long eventTermId, User user);
}
