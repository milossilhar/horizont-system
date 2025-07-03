package sk.leziemevpezinku.spring.api.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegistrationStatus {
    // registration is successfully saved, but waiting for confirmation from user (e.g. by email link)
    CONCEPT("Nepotvrdená"),

    // registration is successfully confirmed by user, but capacity is full
    QUEUE("V poradovníku"),

    // registration is confirmed, either by a system automatically, when event conditions are satisfied or by admin later
    CONFIRMED("Potvrdená");

    private final String description;
}
