package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.model.Payment;
import sk.leziemevpezinku.spring.repo.PaymentRepository;
import sk.leziemevpezinku.spring.service.PaymentService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.math.BigDecimal;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentServiceBean implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public BigDecimal confirmPayment(Long paymentId, Boolean deposit) {
        Payment payment = find(paymentId);

        BigDecimal beforePayment = payment.getRemainingValue();
        payment.setDepositPaid(true);
        if (!Boolean.TRUE.equals(deposit)) {
            payment.setPaid(true);
        }
        BigDecimal afterPayment = payment.getRemainingValue();

        paymentRepository.save(payment);

        return beforePayment.subtract(afterPayment);
    }

    private Payment find(Long id) {
        return checkPayment(this.paymentRepository.findById(id));
    }

    private Payment checkPayment(Optional<Payment> payment) {
        if (payment.isEmpty()) throw CommonException.builder().errorCode(ErrorCode.MSG_NOT_FOUND_PAYMENT).build();
        return payment.get();
    }
}
