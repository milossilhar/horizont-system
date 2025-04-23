package sk.leziemevpezinku.spring.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.service.NotificationService;

@Log4j2
@Service
public class NotificationServiceBean implements NotificationService {
    @Override
    public void sendRegistrationCreatedNotification(Registration registration) {
        log.info("Sending created for registration {}", registration.getUuid());
    }

    @Override
    public void sendRegistrationConfirmedNotification(Registration registration) {
        log.info("Sending confirmation for registration {}", registration.getUuid());
    }
}
