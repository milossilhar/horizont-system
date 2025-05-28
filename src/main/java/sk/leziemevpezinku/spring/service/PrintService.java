package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Registration;

public interface PrintService {
    /** Template names. */
    interface Templates {
        interface Emails {
            String REGISTRATION_CONFIRM = "email-registration-confirm";
        }
    }

    /**
     * Creates email for registration confirmation with data from registration.
     * @param registration registration for which create email
     * @return html code of an email with substituted values from registration
     */
    String printRegistrationConfirmed(Registration registration);
}
