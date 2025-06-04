package sk.leziemevpezinku.spring.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.model.AppParam;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.model.enums.RegistrationStatus;
import sk.leziemevpezinku.spring.service.AppParamService;
import sk.leziemevpezinku.spring.service.NotificationService;
import sk.leziemevpezinku.spring.service.RegistrationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
@RequiredArgsConstructor
public class EmailPaymentInfoScheduler {

    private final AppParamService appParamService;
    private final RegistrationService registrationService;
    private final NotificationService notificationService;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.MINUTES)
    public void sendEmails() {
        log.info("starting scheduler");

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
            BigDecimal deposit = registration.getPayment() == null ? null : registration.getPayment().getDeposit();
            if (deposit == null || BigDecimal.ZERO.equals(deposit)) {
                log.info("not sending email payment for registration {} with zero deposit", registration.getUuid());
                continue;
            }

            if (!RegistrationStatus.CONFIRMED.equals(registration.getStatus())) {
                log.warn("not sending email payment for registration {} with status {}", registration.getUuid(), registration.getStatus());
                continue;
            }

            log.info("sending payment info notification for registration {}", registration.getUuid());
            notificationService.sendPaymentInformationNotification(registration);
        }

        log.info("processed {} registrations", registrations.size());
    }
}
