package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Payment;

public interface PaymentService {

    /**
     * Confirms payment for given payment id
     * @param paymentId payment id
     * @param deposit true if confirming deposit, false if confirming whole payment
     * @return updated payment
     */
    Payment confirmPayment(Long paymentId, Boolean deposit);
}
