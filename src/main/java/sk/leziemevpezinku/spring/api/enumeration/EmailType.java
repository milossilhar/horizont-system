package sk.leziemevpezinku.spring.api.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailType {
    REGISTRATION_CONFIRMATION("Potvrdenie registrácie"),
    PAYMENT_INFO("Platobné informácie"),
    PAYMENT_CONFIRM("Potvrdenie platby");

    private final String subject;
}
