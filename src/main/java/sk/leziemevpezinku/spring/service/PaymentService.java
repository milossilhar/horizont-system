package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Payment;

import java.math.BigDecimal;

public interface PaymentService {

    /**
     * Confirms payment for given payment id
     * @param paymentId payment id
     * @param deposit true if confirming deposit, false if confirming whole payment
     * @return amount confirmed
     */
    BigDecimal confirmPayment(Long paymentId, Boolean deposit);
}
