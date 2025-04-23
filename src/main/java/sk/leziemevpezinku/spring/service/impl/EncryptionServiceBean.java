package sk.leziemevpezinku.spring.service.impl;

import com.nimbusds.jose.*;
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
import sk.leziemevpezinku.spring.util.Base64Util;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Date;

@Log4j2
@Service
public class EncryptionServiceBean implements EncryptionService {

    private static final Integer GCM_IV_LENGTH = 12; //bytes
    private static final Integer GCM_AUTH_TAG_LENGTH = 16; //bytes

    private static final String REGISTRATION_ID_TOKEN_CLAIM_NAME = "registration_id";

    private final String hashSecret;
    private final String encryptionKeyPassphrase;


    public EncryptionServiceBean(
            @Value("${horizon.jwt.secret}") String hashSecret,
            @Value("${horizon.jwt.key}") String encryptionKeyPassphrase
    ) {
        this.hashSecret = hashSecret;
        this.encryptionKeyPassphrase = encryptionKeyPassphrase;
    }

    @Override
    public String generateRegistrationToken(Registration registration) {
        try {
            // creating claims
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(registration.getEmail())
                    .issuer("horizon-system")
                    .expirationTime(new Date(new Date().getTime() + 24 * 60 * 60 * 1000))
                    .notBeforeTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000))
                    .issueTime(new Date())
                    .claim(REGISTRATION_ID_TOKEN_CLAIM_NAME, registration.getId())
                    .build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            // signing
            MACSigner signer = new MACSigner(hashSecret);
            signedJWT.sign(signer);

            // encrypting
            String jwtToken = signedJWT.serialize();
            byte[] encryptedToken = encryptString(jwtToken);

            // encoding to base64
            return Base64Util.encodeUrlNoPadding(encryptedToken);
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
            // decoding from base64
            byte[] encryptedBytes = Base64Util.decodeUrlNoPadding(encryptedToken);

            // decrypting
            String jwtToken = decryptString(encryptedBytes);

            // parsing
            SignedJWT signedJWT = SignedJWT.parse(jwtToken);

            // verification
            JWSVerifier verifier = new MACVerifier(hashSecret);
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

    public byte[] encryptString(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        SecretKey aesKey = generateAesKeyFromPassphrase(encryptionKeyPassphrase);

        Cipher cipher = Cipher.getInstance("AES_256/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_AUTH_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] combinedIvAndEncrypted = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combinedIvAndEncrypted, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combinedIvAndEncrypted, iv.length, encryptedBytes.length);
        return combinedIvAndEncrypted;
    }

    public String decryptString(byte[] combinedIvAndEncrypted) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKey aesKey = generateAesKeyFromPassphrase(encryptionKeyPassphrase);

        byte[] iv = new byte[GCM_IV_LENGTH];
        byte[] encryptedText = new byte[combinedIvAndEncrypted.length - GCM_IV_LENGTH];
        System.arraycopy(combinedIvAndEncrypted, 0, iv, 0, iv.length);
        System.arraycopy(combinedIvAndEncrypted, GCM_IV_LENGTH, encryptedText, 0, encryptedText.length);

        Cipher cipher = Cipher.getInstance("AES_256/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_AUTH_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.DECRYPT_MODE, aesKey, gcmSpec);

        byte[] decryptedBytes = cipher.doFinal(encryptedText);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private SecretKey generateAesKeyFromPassphrase(String passphrase) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha256.digest(passphrase.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "AES");
    }
}
