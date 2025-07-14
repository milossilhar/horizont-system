package sk.leziemevpezinku.spring.service.impl;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.EventTerm;
import sk.leziemevpezinku.spring.model.Payment;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;
import sk.leziemevpezinku.spring.model.enums.EnumerationValues;
import sk.leziemevpezinku.spring.model.enums.EventType;
import sk.leziemevpezinku.spring.model.enums.RegistrationStatus;
import sk.leziemevpezinku.spring.mustache.*;
import sk.leziemevpezinku.spring.service.EnumerationService;
import sk.leziemevpezinku.spring.service.PrintService;
import sk.leziemevpezinku.spring.util.DateUtils;
import sk.leziemevpezinku.spring.util.NumberUtils;
import sk.leziemevpezinku.spring.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class PrintServiceBean implements PrintService {

    private final MustacheResourceTemplateLoader templateLoader;
    private final Mustache.Compiler compiler;
    private final EnumerationService enumerationService;

    @Override
    public String printRegistrationConfirmed(Registration registration) {
        try {
            EmailRegistrationConfirm context = EmailRegistrationConfirm.builder()
                    .name(registration.getName())
                    .surname(registration.getSurname())
                    .status(registration.getStatus().getDescription())
                    .isStatusQueue(RegistrationStatus.QUEUE.equals(registration.getStatus()))
                    .email(registration.getEmail())
                    .telPhone(registration.getTelPhone())
                    .eventName(registration.getEventTerm().getEvent().getName())
                    .createdAt(DateUtils.format(registration.getCreatedAt()))
                    .startDateTime(DateUtils.format(registration.getEventTerm().getStartAt()))
                    .endDateTime(DateUtils.format(registration.getEventTerm().getEndAt()))
                    .location(registration.getEventTerm().getEvent().getPlace())
                    .peopleLength((long) registration.getPeople().size())
                    .consentGDPR(registration.getConsentGDPR())
                    .consentPhoto(registration.getConsentPhoto())
                    .people(registration.getPeople().stream()
                            .map(person -> EmailPerson.builder()
                                    .name(person.getName())
                                    .surname(person.getSurname())
                                    .birthDate(DateUtils.format(person.getDateOfBirth()))
                                    .shirtSize(person.getShirtSize())
                                    .healthNotes(person.getHealthNotes())
                                    .foodAllergyNotes(person.getFoodAllergyNotes())
                                    .build())
                            .toList())
                    .knownPeople(registration.getKnownPeople().stream()
                            .map(knownPerson -> EmailKnownPerson.builder()
                                    .name(knownPerson.getName())
                                    .surname(knownPerson.getSurname())
                                    .relation(enumerationService.getDescription(EnumerationName.REG_E_RELATION, knownPerson.getRelation()))
                                    .build())
                            .toList())
                    .build();

            return executeTemplate(Templates.Emails.REGISTRATION_CONFIRM, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String printPaymentInfo(Registration registration) {
        try {
            final String IBAN = "SK9111000000002947266169";
            final String IBAN_FORMATTED = "SK91 1100 0000 0029 4726 6169";

            Payment payment = registration.getPayment();
            EventTerm eventTerm = registration.getEventTerm();

            String note = StringUtils.strip(registration.getName()) + " " + StringUtils.strip(registration.getSurname());

            EmailPaymentInfo context = EmailPaymentInfo.builder()
                    .name(registration.getName())
                    .surname(registration.getSurname())
                    .eventName(eventTerm.getEvent().getName())
                    .location(eventTerm.getEvent().getPlace())
                    .startDateTime(DateUtils.format(eventTerm.getStartAt()))
                    .endDateTime(DateUtils.format(eventTerm.getEndAt()))
                    .iban(IBAN_FORMATTED)
                    .paymentValue(NumberUtils.formatTwoDecimal(payment.getDeposit()))
                    .note(note)
                    .payBySquareURL(getPayBySquareURL(payment.getDeposit(), LocalDate.now(), payment.getVariableSymbol(), note, IBAN))
                    .depositPerChild(NumberUtils.formatTwoDecimal(eventTerm.getDeposit()))
                    .variableSymbol(payment.getVariableSymbol())
                    .people(registration.getPeople().stream()
                            .map(person -> EmailPerson.builder()
                                    .name(person.getName())
                                    .surname(person.getSurname())
                                    .birthDate(DateUtils.format(person.getDateOfBirth()))
                                    .shirtSize(person.getShirtSize())
                                    .healthNotes(person.getHealthNotes())
                                    .foodAllergyNotes(person.getFoodAllergyNotes())
                                    .build())
                            .toList())
                    .build();

            return executeTemplate(Templates.Emails.PAYMENT_INFO, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String printPaymentConfirm(Registration registration, Boolean deposit, BigDecimal amountPaid) {
        try {
            String paymentSubject = Boolean.TRUE.equals(deposit) ? "záloha" : "platba";
            Payment payment = registration.getPayment();
            EventTerm eventTerm = registration.getEventTerm();

            EmailPaymentConfirm context = EmailPaymentConfirm.builder()
                    .name(registration.getName())
                    .surname(registration.getSurname())
                    .eventName(eventTerm.getEvent().getName())
                    .eventStartDate(DateUtils.format(eventTerm.getStartAt().toLocalDate()))
                    .paymentSubject(paymentSubject)
                    .paymentSubjectCaps(StringUtils.capitalizeFirst(paymentSubject))
                    .paymentValue(NumberUtils.formatTwoDecimal(amountPaid))
                    .variableSymbol(payment.getVariableSymbol())
                    .remainingAmount(NumberUtils.formatTwoDecimal(payment.getRemainingValue()))
                    .depositAmount(NumberUtils.formatTwoDecimal(eventTerm.getDeposit()))
                    .discountAmount(NumberUtils.formatTwoDecimal(payment.getDiscountValue()))
                    .totalAmount(NumberUtils.formatTwoDecimal(payment.getFinalPrice()))
                    .people(registration.getPeople().stream()
                            .map(person -> EmailPerson.builder()
                                    .name(person.getName())
                                    .surname(person.getSurname())
                                    .birthDate(DateUtils.format(person.getDateOfBirth()))
                                    .shirtSize(person.getShirtSize())
                                    .healthNotes(person.getHealthNotes())
                                    .foodAllergyNotes(person.getFoodAllergyNotes())
                                    .build())
                            .toList())
                    .build();

            return executeTemplate(Boolean.TRUE.equals(deposit) ? Templates.Emails.PAYMENT_CONFIRM : Templates.Emails.PAYMENT_COMPLETE_CONFIRM, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String printEventDetail(Registration registration) {
        try {
            final String IBAN = "SK9111000000002947266169";
            final String IBAN_FORMATTED = "SK91 1100 0000 0029 4726 6169";

            Payment payment = registration.getPayment();
            EventTerm eventTerm = registration.getEventTerm();

            String note = StringUtils.strip(registration.getName()) + " " + StringUtils.strip(registration.getSurname());

            EmailEventDetail context = EmailEventDetail.builder()
                    .eventName(eventTerm.getEvent().getName())
                    .name(registration.getName())
                    .surname(registration.getSurname())
                    .startDate(DateUtils.format(eventTerm.getStartAt().toLocalDate()))
                    .endDate(DateUtils.format(eventTerm.getEndAt().toLocalDate()))
                    .discountValue(NumberUtils.formatTwoDecimal(payment.getDiscountValue()))
                    .iban(IBAN_FORMATTED)
                    .variableSymbol(payment.getVariableSymbol())
                    .note(note)
                    .paymentValue(NumberUtils.formatTwoDecimal(payment.getRemainingValue()))
                    .payBySquareURL(getPayBySquareURL(payment.getRemainingValue(), LocalDate.now(), payment.getVariableSymbol(), note, IBAN))
                    .people(registration.getPeople().stream()
                            .map(person -> EmailPerson.builder()
                                    .name(person.getName())
                                    .surname(person.getSurname())
                                    .birthDate(DateUtils.format(person.getDateOfBirth()))
                                    .shirtSize(person.getShirtSize())
                                    .healthNotes(Objects.requireNonNullElse(person.getHealthNotes(), "-"))
                                    .foodAllergyNotes(Objects.requireNonNullElse(person.getFoodAllergyNotes(), "-"))
                                    .build())
                            .toList())
                    .knownPeople(registration.getKnownPeople().stream()
                            .map(knownPerson -> EmailKnownPerson.builder()
                                    .name(knownPerson.getName())
                                    .surname(knownPerson.getSurname())
                                    .relation(enumerationService.getDescription(EnumerationName.REG_E_RELATION, knownPerson.getRelation()))
                                    .build())
                            .toList())
                    .build();

            if (EventType.CAMP.equals(eventTerm.getEvent().getEventType())) {
                return executeTemplate(Templates.Emails.EVENT_CAMP_DETAIL, context);
            } else if (EventType.EVENT.equals(eventTerm.getEvent().getEventType())) {
                return executeTemplate(Templates.Emails.EVENT_WEEK_CAMP_DETAIL, context);
            }
            throw new IllegalArgumentException("Unknown event type: " + eventTerm.getEvent().getEventType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getPayBySquareURL(BigDecimal amount, LocalDate dueDate, String variableSymbol, String note, String iban) {
        String template = "https://api.freebysquare.sk/pay/v1/generate-png?size=150&color=3&transparent=false&amount={{amount}}&dueDate={{dueDate}}&variableSymbol={{variableSymbol}}&paymentNote={{note}}&iban={{iban}}&";

        HashMap<String, String> context = new HashMap<>();
        context.put("amount", new DecimalFormat("#0.##").format(amount.setScale(2, RoundingMode.HALF_DOWN)));
        context.put("dueDate", DateUtils.formatCompact(dueDate));
        context.put("variableSymbol", variableSymbol);
        context.put("note", StringUtils.urlEncode(note));
        context.put("iban", iban);

        return executeString(template, context);
    }

    private String executeTemplate(String templateName, Object context) throws Exception {
        var reader = templateLoader.getTemplate(templateName);
        var template = compiler.compile(reader);
        return template.execute(context);
    }

    private String executeString(String templateString, Object context) {
        Template template = compiler.compile(templateString);
        return template.execute(context);
    }
}
