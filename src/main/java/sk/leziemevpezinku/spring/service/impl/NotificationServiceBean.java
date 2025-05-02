package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.service.NotificationService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

@Log4j2
@Service
@RequiredArgsConstructor
public class NotificationServiceBean implements NotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendRegistrationCreatedNotification(Registration registration) {
        log.info("Sending created for registration {}", registration.getUuid());
    }

    @Override
    public void sendRegistrationConfirmedNotification(Registration registration) {
        log.info("Sending confirmation for registration {}", registration.getUuid());
    }

    @Override
    public void sendTestMail() {
        log.info("sendTestMail - called");
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("#leziemevpezinku <" + sender + ">");
            message.setTo("migmig095@gmail.com");
            message.setSubject("spring - test");
            message.setText("Testing spring::boot");

            log.info("sendTestMail - sending mail");
            mailSender.send(message);

        } catch (Exception e) {
            log.error("error sending test mail", e);
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_GENERIC_ERROR)
                    .build();
        }
    }
}
