package sk.leziemevpezinku.spring.service;

import org.junit.jupiter.api.Test;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.service.impl.EncryptionServiceBean;
import sk.leziemevpezinku.spring.service.model.RegistrationTokenClaim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@UnitTest
public class EncryptionServiceTest {

    private final EncryptionService encryptionService = new EncryptionServiceBean(
            "4VSSZWfgwMXMmNvdhohxLYfmfpVcR+ME4Ci6G4Yx92MtILDjVpu/+pq7yDPL8/CCAmg7QzFhlfjsaAmHVpQJKQ==",
            "AHOJ"
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

        RegistrationTokenClaim claim = encryptionService.validateRegistrationToken(token);

        assertNotNull(claim);
        assertEquals(120L, claim.getId());
        assertEquals("leziemevpezinku@gmail.com", claim.getEmail());
    }
}
