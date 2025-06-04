package sk.leziemevpezinku.spring.repo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.leziemevpezinku.spring.model.enums.RegistrationStatus;

import static sk.leziemevpezinku.spring.model.enums.RegistrationStatus.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTermCapacity {
    private Long eventTermId;
    private RegistrationStatus status;
    private Integer capacity;
    private Long registrations;

    public Long getConfirmedCount() {
        if (CONFIRMED.equals(status)) {
            return registrations;
        }

        return 0L;
    }

    public Long getRegisteredCount() {
        if (CONFIRMED.equals(status) || QUEUE.equals(status)) {
            return registrations;
        }
        return 0L;
    }
}
