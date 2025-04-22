package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.service.model.RegistrationTokenClaim;

public interface EncryptionService {

    /**
     *
     * @param registration
     * @return
     */
    String generateRegistrationToken(Registration registration);

    /**
     *
     * @param encryptedToken
     */
    RegistrationTokenClaim validateRegistrationToken(String encryptedToken);
}
