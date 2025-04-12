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

    // registration messages
    MSG_REG_SOON(HttpStatus.BAD_REQUEST, "Reservation is not yet possible."),
    MSG_REG_DEADLINE(HttpStatus.BAD_REQUEST, "Reservation is already closed."),
    MSG_REG_ALREADY_EXISTS(HttpStatus.CONFLICT, "Reservation for given person and event term already exists.");

    private final HttpStatus status;

    private final String message;
}
