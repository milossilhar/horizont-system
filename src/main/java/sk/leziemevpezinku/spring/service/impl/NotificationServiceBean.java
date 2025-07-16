package sk.leziemevpezinku.spring.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.model.EmailLog;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.repo.EmailLogRepository;
import sk.leziemevpezinku.spring.service.NotificationService;
import sk.leziemevpezinku.spring.service.PrintService;
import sk.leziemevpezinku.spring.service.RegistrationService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.EmailData;
import sk.leziemevpezinku.spring.service.model.EmailType;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

@Log4j2
@Service
@RequiredArgsConstructor
public class NotificationServiceBean implements NotificationService {

    private final JavaMailSender mailSender;
    private final PrintService printService;
    private final RegistrationService registrationService;
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
        if (Boolean.TRUE.equals(registration.getEmailPaymentInfoSent())) {
            log.info("Registration confirmed email for registration {} already sent.", registration.getUuid());
            return;
        }

        try {
            String emailBody = printService.printRegistrationConfirmed(registration);
            sendHtmlEmail(
                    EmailData.builder()
                            .emailType(EmailType.REGISTRATION_CONFIRMATION)
                            .htmlBody(emailBody)
                            .build(),
                    registration);
            updateRegistrationFlag(registration, Registration::setEmailConfirmSent);
        } catch (Exception ex) {
            log.error("Error sending registration confirmed notification for registration {}", registration.getUuid(), ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void sendPaymentInformationNotification(Registration registration) {
        if (Boolean.TRUE.equals(registration.getEmailPaymentInfoSent())) {
            log.info("Payment information email for registration {} already sent.", registration.getUuid());
            return;
        }

        try {
            String emailBody = printService.printPaymentInfo(registration);
            sendHtmlEmail(
                    EmailData.builder()
                            .emailType(EmailType.PAYMENT_INFO)
                            .htmlBody(emailBody)
                            .build(),
                    registration);
            updateRegistrationFlag(registration, Registration::setEmailPaymentInfoSent);
        } catch (Exception ex) {
            log.error("Error sending payment information notification for registration {}", registration.getUuid(), ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void sendPaymentConfirmationNotification(Registration registration, Boolean deposit, BigDecimal amountPaid) {
        if (Boolean.TRUE.equals(deposit) && Boolean.TRUE.equals(registration.getEmailPaymentConfirmSent())) {
            log.info("Payment deposit confirmation email for registration {} already sent.", registration.getUuid());
            return;
        }
        if (!Boolean.TRUE.equals(deposit) && Boolean.TRUE.equals(registration.getEmailPaymentCompleteConfirmSent())) {
            log.info("Payment complete confirmation email for registration {} already sent.", registration.getUuid());
            return;
        }

        try {
            String emailBody = printService.printPaymentConfirm(registration, deposit, amountPaid);
            sendHtmlEmail(
                    EmailData.builder()
                            .emailType(EmailType.PAYMENT_CONFIRM)
                            .htmlBody(emailBody)
                            .build(),
                    registration);
            updateRegistrationFlag(registration, Registration::setEmailPaymentConfirmSent);
        } catch (Exception ex) {
            log.error("Error sending payment confirmation notification for registration {}", registration.getUuid(), ex);
        }
    }

    @Override
    public void sendEventDetailNotification(Registration registration) {
        if (Boolean.TRUE.equals(registration.getEmailDetailSent())) {
            log.info("Event detail email for registration {} already sent.", registration.getUuid());
            return;
        }

        try {
            String emailBody = printService.printEventDetail(registration);
            sendHtmlEmail(
                    EmailData.builder()
                            .emailType(EmailType.EVENT_DETAIL)
                            .subjectAddon(registration.getEventTerm().getEvent().getName())
                            .attachmentFilename("vyhlasenie.pdf")
                            .attachmentSource("/attachments/sposobilost.pdf")
                            .htmlBody(emailBody)
                            .build(),
                    registration);
            updateRegistrationFlag(registration, Registration::setEmailDetailSent);
        } catch (Exception ex) {
            log.error("Error sending event detail notification for registration {}", registration.getUuid(), ex);
        }
    }

    private void updateRegistrationFlag(Registration registration, BiConsumer<Registration, Boolean> consumer) {
        registrationService.updateFlagNewTx(registration.getId(), consumer);
        consumer.accept(registration, true);
    }

    private void sendHtmlEmail(EmailData emailData, Registration registration) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("#leziemevpezinku <" + sender + ">");
            helper.setTo(registration.getEmail());

            helper.setSubject(emailData.getSubject());
            helper.setText(emailData.getHtmlBody(), true);

            if (emailData.hasAttachment()) {
                helper.addAttachment(emailData.getAttachmentFilename(), emailData.getAttachmentFile());
            }

            log.debug("sending email {} for registration {}", emailData.getEmailName(), registration.getUuid());
            mailSender.send(message);

            emailLogRepository.save(EmailLog.builder()
                    .registrationId(registration.getId())
                    .emailType(emailData.getEmailName())
                    .recipients(registration.getEmail())
                    .html(emailData.getHtmlBody())
                    .build());

        } catch (Exception e) {
            log.error("error sending mail {} for registration {}", emailData.getEmailName(), registration.getUuid(), e);
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_GENERIC_ERROR)
                    .build();
        }
    }
}
