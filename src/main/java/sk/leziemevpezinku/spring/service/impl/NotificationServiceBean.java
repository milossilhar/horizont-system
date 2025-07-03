package sk.leziemevpezinku.spring.service.impl;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.model.EmailLog;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.repo.EmailLogRepository;
import sk.leziemevpezinku.spring.service.NotificationService;
import sk.leziemevpezinku.spring.service.PrintService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.EmailType;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

@Log4j2
@Service
@RequiredArgsConstructor
public class NotificationServiceBean implements NotificationService {

    private final JavaMailSender mailSender;
    private final PrintService printService;
    private final EmailLogRepository emailLogRepository;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void sendRegistrationCreatedNotification(Registration registration) {
        log.warn("sendRegistrationCreatedNotification is not supported at the moment");
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void sendRegistrationConfirmedNotification(Registration registration) {
        try {
            String emailBody = printService.printRegistrationConfirmed(registration);
            sendHtmlEmail(EmailType.REGISTRATION_CONFIRMATION, emailBody, registration);
        } catch (Exception ex) {
            log.error("Error sending registration confirmed notification for registration {}", registration.getUuid(), ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void sendPaymentInformationNotification(Registration registration) {
        try {
            String emailBody = printService.printPaymentInfo(registration);
            sendHtmlEmail(EmailType.PAYMENT_INFO, emailBody, registration);
        } catch (Exception ex) {
            log.error("Error sending payment information notification for registration {}", registration.getUuid(), ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void sendPaymentConfirmationNotification(Registration registration) {
        try {
            String emailBody = printService.printPaymentConfirm(registration);
            sendHtmlEmail(EmailType.PAYMENT_CONFIRM, emailBody, registration);
        } catch (Exception ex) {
            log.error("Error sending payment confirmation notification for registration {}", registration.getUuid(), ex);
        }
    }

    private void sendHtmlEmail(EmailType emailType, String htmlBody, Registration registration) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setFrom("#leziemevpezinku <" + sender + ">");
            message.setRecipients(Message.RecipientType.TO, registration.getEmail());

            message.setSubject(emailType.getSubject());
            message.setContent(htmlBody, "text/html; charset=utf-8");

            log.debug("sending email {} for registration {}", emailType.name(), registration.getUuid());
            mailSender.send(message);

            emailLogRepository.save(EmailLog.builder()
                    .registrationId(registration.getId())
                    .emailType(emailType.name())
                    .recipients(registration.getEmail())
                    .html(htmlBody)
                    .build());

        } catch (Exception e) {
            log.error("error sending mail {} for registration {}", emailType.name(), registration.getUuid(), e);
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_GENERIC_ERROR)
                    .build();
        }
    }
}
