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
    MSG_REG_SOON(HttpStatus.BAD_REQUEST, "Rezervácia ešte neni povolená."),
    MSG_REG_DEADLINE(HttpStatus.BAD_REQUEST, "Rezervácia je už uzatvorená."),
    MSG_REG_ALREADY_EXISTS(HttpStatus.CONFLICT, "Rezervácia pre osobu {{fullname}} je už vytvorená, skúste odstrániť túto osobu a vytvoriť registráciu znova.");

    private final HttpStatus status;

    private final String message;
}
