package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.api.dto.RegistrationTokenClaimDTO;

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
    RegistrationTokenClaimDTO validateRegistrationToken(String encryptedToken);
}
