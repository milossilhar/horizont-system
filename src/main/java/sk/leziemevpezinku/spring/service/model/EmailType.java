package sk.leziemevpezinku.spring.service.model;

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
