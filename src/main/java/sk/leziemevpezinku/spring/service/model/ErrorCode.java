package sk.leziemevpezinku.spring.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // not found messages
    MSG_NOT_FOUND_EVENT(HttpStatus.NOT_FOUND, "Event with id {{id}} was not found."),
    MSG_NOT_FOUND_EVENT_TERM(HttpStatus.NOT_FOUND, "Event term with id {{id}} was not found."),
    MSG_NOT_FOUND_REGISTRATION(HttpStatus.NOT_FOUND, "Registration with id {{id}} was not found."),
    MSG_NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "Payment with id {{id}} was not found."),

    // registration messages
    MSG_REG_SOON(HttpStatus.BAD_REQUEST, "Registration is not yet possible."),
    MSG_REG_DEADLINE(HttpStatus.BAD_REQUEST, "Registration is already closed."),
    MSG_REG_ALREADY_EXISTS(HttpStatus.CONFLICT, "Registration for given person and event term already exists."),
    MSG_REG_CONFIRM_BAD_STATUS(HttpStatus.BAD_REQUEST, "Registration is not in desired status to confirm."),

    // encryption
    MSG_REG_TOKEN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Registration token generation/verification failed."),
    MSG_REG_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Registration token is invalid."),

    MSG_ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "Access denied."),
    MSG_NOT_FOUND(HttpStatus.NOT_FOUND, "Not found."),
    MSG_GENERIC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Generic server error.");

    private final HttpStatus status;

    private final String message;
}
