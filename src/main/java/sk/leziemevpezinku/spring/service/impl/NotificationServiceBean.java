package sk.leziemevpezinku.spring.service.impl;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.service.NotificationService;
import sk.leziemevpezinku.spring.service.PrintService;
import sk.leziemevpezinku.spring.service.RegistrationService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

@Log4j2
@Service
@RequiredArgsConstructor
public class NotificationServiceBean implements NotificationService {

    private final JavaMailSender mailSender;
    private final PrintService printService;
    private final RegistrationService registrationService;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendRegistrationCreatedNotification(Registration registration) {
        log.warn("sendRegistrationCreatedNotification is not supported at the moment");
    }

    @Override
    public void sendRegistrationConfirmedNotification(Registration registration) {
        try {
            String emailBody = printService.printRegistrationConfirmed(registration);
            sendHtmlEmail("Potvrdenie registrácie", emailBody, registration);
            registrationService.updateFlag(registration.getId(), Registration::setEmailConfirmSent);
        } catch (Exception ex) {
            log.error("Error sending registration confirmed notification for registration {}", registration.getUuid(), ex);
        }
    }

    @Override
    public void sendPaymentInformationNotification(Registration registration) {
        try {
            String emailBody = printService.printPaymentInfo(registration);
            sendHtmlEmail("Platobné informácie", emailBody, registration);
            registrationService.updateFlag(registration.getId(), Registration::setEmailPaymentInfoSent);
        } catch (Exception ex) {
            log.error("Error sending payment information notification for registration {}", registration.getUuid(), ex);
        }
    }

    private void sendHtmlEmail(String subject, String htmlBody, Registration registration) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setFrom("#leziemevpezinku <" + sender + ">");
            message.setRecipients(Message.RecipientType.TO, registration.getEmail());

            message.setSubject(subject);
            message.setContent(htmlBody, "text/html; charset=utf-8");

            log.debug("sending email {} for registration {}", subject, registration.getUuid());
            mailSender.send(message);

        } catch (Exception e) {
            log.error("error sending mail {} for registration {}", subject, registration.getUuid(), e);
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_GENERIC_ERROR)
                    .build();
        }
    }
}
