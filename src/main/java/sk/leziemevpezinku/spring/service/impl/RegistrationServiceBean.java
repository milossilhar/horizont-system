package sk.leziemevpezinku.spring.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.model.*;
import sk.leziemevpezinku.spring.model.enums.RegistrationStatus;
import sk.leziemevpezinku.spring.repo.EventTermRepository;
import sk.leziemevpezinku.spring.repo.RegistrationRepository;
import sk.leziemevpezinku.spring.service.EncryptionService;
import sk.leziemevpezinku.spring.service.RegistrationService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;
import sk.leziemevpezinku.spring.service.model.RegistrationTokenClaim;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;

import static sk.leziemevpezinku.spring.model.enums.EnumerationValues.REG_E_EVENT_DISCOUNT_TYPE.LETO_TABOR_25;

@Log4j2
@Service
@AllArgsConstructor
public class RegistrationServiceBean implements RegistrationService {

    private final EventTermRepository eventTermRepository;
    private final RegistrationRepository registrationRepository;
    private final EncryptionService encryptionService;

    @Override
    @Transactional
    public Registration getById(Long id) {
        return findRegistration(id);
    }

    @Override
    @Transactional
    public Registration getByPaymentId(Long paymentId) {
        return findByPayment(paymentId);
    }

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

        Integer numberOfPeopleRegistered = eventTerm.getRegistrations().stream()
                .reduce(0, (subtotal, reg) -> subtotal + reg.getPeople().size(), Integer::sum);

        // calculate and create payment
        Payment payment = calculatePriceForRegistration(eventTerm, registration.getEmail(), Long.valueOf(registration.getPeople().size()));
        registration.setPayment(payment);
        payment.setRegistration(registration);

        registration.setStatus(RegistrationStatus.CONFIRMED);

        if (numberOfPeopleRegistered + registration.getPeople().size() > eventTerm.getCapacity()) {
            registration.setStatus(RegistrationStatus.QUEUE);
        }

        registration.setEventTerm(eventTerm);
        eventTerm.getRegistrations().add(registration);

        return registrationRepository.save(registration);
    }

    @Override
    @Transactional
    public Registration confirmRegistration(String jwtToken) {
        RegistrationTokenClaim registrationTokenClaim = encryptionService.validateRegistrationToken(jwtToken);

        Registration registration = findRegistration(registrationTokenClaim.getId());

        if (!registration.getEmail().equals(registrationTokenClaim.getEmail())) {
            log.error("Mismatch in email claim {} from JWT token and registration (id: {}) email {}",
                    registrationTokenClaim.getEmail(),
                    registration.getId(),
                    registration.getEmail());

            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_REG_TOKEN_INVALID)
                    .build();
        }

        if (RegistrationStatus.QUEUE.equals(registration.getStatus())) {
            log.warn("Cannot confirm registration in QUEUE status.");

            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_REG_CONFIRM_BAD_STATUS)
                    .build();
        }

        if (!RegistrationStatus.CONCEPT.equals(registration.getStatus())) {
            return registration;
        }

        registration.setStatus(RegistrationStatus.CONFIRMED);
        return registrationRepository.save(registration);
    }

    @Override
    @Transactional
    public Payment calculatePriceForRegistration(Long eventTermId, String userEmail, Long numberOfPeople) {
        EventTerm eventTerm = findEventTerm(eventTermId);

        return calculatePriceForRegistration(eventTerm, userEmail, numberOfPeople);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateFlagNewTx(Long id, BiConsumer<Registration, Boolean> consumer) {
        Registration registration = findUninitializedRegistration(id);

        consumer.accept(registration, true);

        registrationRepository.save(registration);
    }

    @Override
    @Transactional
    public List<Registration> findForPaymentInfo(Long batchSize) {
        if (batchSize == null) return Collections.emptyList();

        return registrationRepository.findForDepositPaymentInfoEmail(Limit.of(batchSize.intValue()));
    }

    @Override
    public List<Registration> findForEventDetail(Long batchSize) {
        if (batchSize == null) return Collections.emptyList();

        return registrationRepository.findForEventDetailEmail(Limit.of(batchSize.intValue()));
    }

    private Payment calculatePriceForRegistration(EventTerm eventTerm, String userEmail, Long numberOfPeople) {
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

    private Registration checkRegistration(Optional<Registration> registration) {
        if (registration.isEmpty()) throw CommonException.builder().errorCode(ErrorCode.MSG_NOT_FOUND_REGISTRATION).build();
        return registration.get();
    }

    private Registration findUninitializedRegistration(Long registrationId) {
        Optional<Registration> registration = registrationRepository.findUninitializedById(registrationId);
        return checkRegistration(registration);
    }

    private Registration findRegistration(Long registrationId) {
        Optional<Registration> registration = registrationRepository.findById(registrationId);
        return checkRegistration(registration);
    }

    private Registration findByPayment(Long paymentId) {
        Optional<Registration> registration = registrationRepository.findByPaymentId(paymentId);
        return checkRegistration(registration);
    }
}
