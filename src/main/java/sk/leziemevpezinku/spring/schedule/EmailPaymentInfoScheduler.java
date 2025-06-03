package sk.leziemevpezinku.spring.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sk.leziemevpezinku.spring.model.AppParam;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.service.AppParamService;
import sk.leziemevpezinku.spring.service.NotificationService;
import sk.leziemevpezinku.spring.service.RegistrationService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@RequiredArgsConstructor
@Component
public class EmailPaymentInfoScheduler {

    private final AppParamService appParamService;
    private final RegistrationService registrationService;
    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.MINUTES, initialDelay = 10000)
    public void sendEmails() {
        if (!appParamService.hasEnabledFeature(AppParam.Names.SCHEDULE_PAYMENT_INFO_ENABLE)) {
            log.info("scheduler disabled - nothing to be done");
            return;
        }

        Long batchSize = appParamService.getNumericValue(AppParam.Names.SCHEDULE_PAYMENT_INFO_BATCH_SIZE);
        log.info("starting with batch size {}", batchSize);

        List<Registration> registrations = registrationService.findForPaymentInfo(batchSize);

        if (registrations.isEmpty()) {
            log.info("found 0 registrations - nothing to be done");
            return;
        }

        for (Registration registration : registrations) {
            log.info("sending payment info notification for registration {}", registration.getUuid());
            notificationService.sendPaymentInformationNotification(registration);
        }

        log.info("processed {} registrations", registrations.size());
    }
}
