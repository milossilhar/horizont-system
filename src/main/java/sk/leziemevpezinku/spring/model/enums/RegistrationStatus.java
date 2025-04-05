package sk.leziemevpezinku.spring.model.enums;

public enum RegistrationStatus {
    // registration is successfully submitted by a user, but there was a problem with conditions
    WAITING,

    // registration is accepted, either by a system automatically, when all conditions are satisfied or by admin
    ACCEPTED,

    // registration is confirmed by admin in the system
    CONFIRMED
}
