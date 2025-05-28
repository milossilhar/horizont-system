package sk.leziemevpezinku.spring.service.impl;

import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;
import sk.leziemevpezinku.spring.model.enums.RegistrationStatus;
import sk.leziemevpezinku.spring.mustache.EmailRegistrationConfirmKnownPerson;
import sk.leziemevpezinku.spring.mustache.EmailRegistrationConfirmPerson;
import sk.leziemevpezinku.spring.service.EnumerationService;
import sk.leziemevpezinku.spring.service.PrintService;
import sk.leziemevpezinku.spring.mustache.EmailRegistrationConfirm;
import sk.leziemevpezinku.spring.util.DateUtils;

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
                            .map(person -> EmailRegistrationConfirmPerson.builder()
                                    .name(person.getName())
                                    .surname(person.getSurname())
                                    .birthDate(DateUtils.format(person.getDateOfBirth()))
                                    .shirtSize(person.getShirtSize())
                                    .healthNotes(person.getHealthNotes())
                                    .foodAllergyNotes(person.getFoodAllergyNotes())
                                    .build())
                            .toList())
                    .knownPeople(registration.getKnownPeople().stream()
                            .map(knownPerson -> EmailRegistrationConfirmKnownPerson.builder()
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

    private String executeTemplate(String templateName, Object context) throws Exception {
        var reader = templateLoader.getTemplate(templateName);
        var template = compiler.compile(reader);
        return template.execute(context);
    }
}
