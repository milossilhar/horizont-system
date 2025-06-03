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
import sk.leziemevpezinku.spring.model.enums.RegistrationStatus;
import sk.leziemevpezinku.spring.mustache.EmailKnownPerson;
import sk.leziemevpezinku.spring.mustache.EmailPaymentInfo;
import sk.leziemevpezinku.spring.mustache.EmailPerson;
import sk.leziemevpezinku.spring.service.EnumerationService;
import sk.leziemevpezinku.spring.service.PrintService;
import sk.leziemevpezinku.spring.mustache.EmailRegistrationConfirm;
import sk.leziemevpezinku.spring.util.DateUtils;
import sk.leziemevpezinku.spring.util.NumberUtils;
import sk.leziemevpezinku.spring.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;

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
                    .isStatusConcept(RegistrationStatus.CONCEPT.equals(registration.getStatus()))
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
                    .startDateTime(DateUtils.format(eventTerm.getStartAt()))
                    .endDateTime(DateUtils.format(eventTerm.getEndAt()))
                    .iban(IBAN_FORMATTED)
                    .paymentValue(NumberUtils.formatTwoDecimal(payment.getDeposit()))
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

    private String getPayBySquareURL(BigDecimal amount, LocalDate dueDate, String variableSymbol, String note, String iban) throws Exception {
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

    private String executeString(String templateString, Object context) throws Exception {
        Template template = compiler.compile(templateString);
        return template.execute(context);
    }
}
