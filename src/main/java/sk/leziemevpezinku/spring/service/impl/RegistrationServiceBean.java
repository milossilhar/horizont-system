package sk.leziemevpezinku.spring.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.model.*;
import sk.leziemevpezinku.spring.repo.EventTermRepository;
import sk.leziemevpezinku.spring.repo.RegistrationRepository;
import sk.leziemevpezinku.spring.service.RegistrationService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class RegistrationServiceBean implements RegistrationService {

    private final EventTermRepository eventTermRepository;
    private final RegistrationRepository registrationRepository;

    @Override
    @Transactional
    public Registration createRegistration(Long eventTermId, Registration registration) {
        LocalDateTime now = LocalDateTime.now();

        Optional<EventTerm> eventTermOptional = eventTermRepository.findById(eventTermId);

        if (eventTermOptional.isEmpty()) throw CommonException.builder().errorCode(ErrorCode.MSG_NOT_FOUND_EVENT_TERM).build();

        EventTerm eventTerm = eventTermOptional.get();

        // check event registration window
        Event event = eventTerm.getEvent();

        if (now.isBefore(event.getRegStartAt())) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_REG_SOON)
                    .build();
        }

        if (now.isAfter(event.getRegEndAt())) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_REG_DEADLINE)
                    .build();
        }

        // check if there is same registration
        for (Person registrationPerson : registration.getPeople()) {
            for (Registration eventTermRegistration : eventTerm.getRegistrations()) {
                if (eventTermRegistration.hasPerson(registrationPerson)) {
                    throw CommonException.builder()
                            .errorCode(ErrorCode.MSG_REG_ALREADY_EXISTS)
                            .build();
                }
            }
        }

        return registrationRepository.save(registration);
    }

    @Override
    public Registration confirmRegistration(String jwtToken) {
        return null;
    }
}
