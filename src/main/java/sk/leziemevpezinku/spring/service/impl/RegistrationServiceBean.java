package sk.leziemevpezinku.spring.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.model.*;
import sk.leziemevpezinku.spring.repo.EventRepository;
import sk.leziemevpezinku.spring.repo.EventTermRepository;
import sk.leziemevpezinku.spring.repo.RegistrationRepository;
import sk.leziemevpezinku.spring.service.RegistrationService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static sk.leziemevpezinku.spring.model.enums.EnumerationValues.REG_E_EVENT_DISCOUNT_TYPE.LETO_TABOR_25;

@Service
@AllArgsConstructor
public class RegistrationServiceBean implements RegistrationService {

    private final EventTermRepository eventTermRepository;
    private final RegistrationRepository registrationRepository;

    @Override
    @Transactional
    public Registration createRegistration(Long eventTermId, Registration registration) {
        LocalDateTime now = LocalDateTime.now();

        EventTerm eventTerm = findForUpdateEventTerm(eventTermId);

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

    @Override
    public Payment calculatePriceForRegistration(Long eventTermId, String userEmail, Long numberOfPeople) {
        EventTerm eventTerm = findEventTerm(eventTermId);

        Payment payment = Payment.builder()
                .price(eventTerm.getPrice().multiply(BigDecimal.valueOf(numberOfPeople)))
                .deposit(eventTerm.getDeposit().multiply(BigDecimal.valueOf(numberOfPeople)))
                .build();

        Event event = eventTerm.getEvent();
        String discountType = event.getDiscountType();

        if (LETO_TABOR_25.equals(discountType)) {
            if (numberOfPeople == 2) payment.setDiscountValue(BigDecimal.valueOf(20));
            if (numberOfPeople > 2) payment.setDiscountValue(BigDecimal.valueOf(50));
        }

        return payment;
    }

    private EventTerm findEventTerm(Long eventTermId) {
        Optional<EventTerm> eventTermOptional = eventTermRepository.findById(eventTermId);

        if (eventTermOptional.isEmpty()) throw CommonException.builder().errorCode(ErrorCode.MSG_NOT_FOUND_EVENT_TERM).build();

        return eventTermOptional.get();
    }

    private EventTerm findForUpdateEventTerm(Long eventTermId) {
        Optional<EventTerm> eventTermOptional = eventTermRepository.findForUpdateById(eventTermId);

        if (eventTermOptional.isEmpty()) throw CommonException.builder().errorCode(ErrorCode.MSG_NOT_FOUND_EVENT_TERM).build();

        return eventTermOptional.get();
    }
}
