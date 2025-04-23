package sk.leziemevpezinku.spring.service;

import org.junit.jupiter.api.Test;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.service.impl.EncryptionServiceBean;
import sk.leziemevpezinku.spring.service.model.RegistrationTokenClaim;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
public class EncryptionServiceTest {

    private final EncryptionService encryptionService = new EncryptionServiceBean(
            "HashSecretHasToHaveMinimalLengthOf32",
            "SuperDuperPassphrase"
    );

    @Test
    public void encryptionServiceTest() {
        String token = encryptionService.generateRegistrationToken(
                Registration.builder()
                        .id(120L)
                        .email("leziemevpezinku@gmail.com")
                        .build()
        );

        assertNotNull(token);
        assertTrue(token.length() < 1000, "Generated token has length more than 1000 characters.");

        RegistrationTokenClaim claim = encryptionService.validateRegistrationToken(token);

        assertNotNull(claim);
        assertEquals(120L, claim.getId());
        assertEquals("leziemevpezinku@gmail.com", claim.getEmail());
    }
}
