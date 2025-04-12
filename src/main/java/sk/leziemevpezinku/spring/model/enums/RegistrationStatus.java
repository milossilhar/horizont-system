package sk.leziemevpezinku.spring.model.enums;

public enum RegistrationStatus {
    // registration is successfully saved, but waiting for confirmation from user (e.g. by email link)
    CONCEPT,

    // registration is successfully confirmed by user, but capacity is full
    QUEUE,

    // registration is accepted, either by a system automatically, when capacity condition is satisfied or by admin later
    ACCEPTED,

    // registration is confirmed by admin in the system
    CONFIRMED
}
