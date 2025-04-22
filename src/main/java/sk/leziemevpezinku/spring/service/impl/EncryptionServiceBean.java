package sk.leziemevpezinku.spring.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.service.EncryptionService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;
import sk.leziemevpezinku.spring.service.model.RegistrationTokenClaim;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Log4j2
@Service
public class EncryptionServiceBean implements EncryptionService {

    private final String REGISTRATION_ID_TOKEN_CLAIM_NAME = "registration_id";

    private final byte[] registrationTokenHashSecret;
    private final SecretKey registrationTokenEncryptionKey;

    public EncryptionServiceBean(
            @Value("${horizon.jwt.secret}") String tokenInBase64,
            @Value("${horizon.jwt.key}") String keyInBase64
    ) {
        this.registrationTokenHashSecret = Base64.getDecoder().decode(tokenInBase64);
        this.registrationTokenEncryptionKey = new SecretKeySpec(Base64.getDecoder().decode(keyInBase64), "AES");
    }

    @Override
    public String generateRegistrationToken(Registration registration) {
        try {
            // create signed JWT token
            MACSigner signer = new MACSigner(registrationTokenHashSecret);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(registration.getEmail())
                    .issuer("horizon-system")
                    .expirationTime(new Date(new Date().getTime() + 24 * 60 * 60 * 1000))
                    .notBeforeTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000))
                    .issueTime(new Date())
                    .claim(REGISTRATION_ID_TOKEN_CLAIM_NAME, registration.getId())
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), claimsSet);

            signedJWT.sign(signer);

            // encrypt generated JWT token
            JWEHeader encryptionHeader = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM);
            JWEObject jweObject = new JWEObject(encryptionHeader, new Payload(signedJWT));
            jweObject.encrypt(new DirectEncrypter(registrationTokenEncryptionKey));

            return jweObject.serialize();
        } catch (Exception e) {
            log.error("Error generating token for registration: {}", registration, e);
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_REG_TOKEN_EXCEPTION)
                    .build();
        }
    }

    @Override
    public RegistrationTokenClaim validateRegistrationToken(String encryptedToken) {
        try {
            JWEObject jweObject = JWEObject.parse(encryptedToken);
            jweObject.decrypt(new DirectDecrypter(registrationTokenEncryptionKey));

            SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
            JWSVerifier verifier = new MACVerifier(registrationTokenHashSecret);

            // verification
            signedJWT.verify(verifier);

            JWTClaimsSet jwtClaims = signedJWT.getJWTClaimsSet();

            return RegistrationTokenClaim.builder()
                    .email(jwtClaims.getSubject())
                    .id(jwtClaims.getLongClaim(REGISTRATION_ID_TOKEN_CLAIM_NAME))
                    .build();
        } catch (Exception e) {
            log.error("Error validating registration token", e);
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_REG_TOKEN_EXCEPTION)
                    .build();
        }
    }
}
