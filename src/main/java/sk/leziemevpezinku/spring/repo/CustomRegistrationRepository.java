package sk.leziemevpezinku.spring.repo;

import org.springframework.data.domain.Limit;
import sk.leziemevpezinku.spring.model.Registration;

import java.util.List;

public interface CustomRegistrationRepository {

    /**
     * Find registrations to send deposit payment info emails
     * @param limit limit the results
     * @return found registrations
     */
    List<Registration> findForDepositPaymentInfoEmail(Limit limit);
}
