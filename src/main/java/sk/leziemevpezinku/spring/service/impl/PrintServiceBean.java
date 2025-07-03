package sk.leziemevpezinku.spring.service.impl;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.mustache.*;
import sk.leziemevpezinku.spring.service.PrintService;
import sk.leziemevpezinku.spring.util.DateUtils;
import sk.leziemevpezinku.spring.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;

@Log4j2
@Service
@RequiredArgsConstructor
public class PrintServiceBean implements PrintService {

    private final MustacheResourceTemplateLoader templateLoader;
    private final Mustache.Compiler compiler;

    @Override
    public String printRegistrationConfirmed(Registration registration) {
        try {
            EmailRegistrationConfirm context = EmailRegistrationConfirm.builder()
                    .build();

            return executeTemplate(Templates.Emails.REGISTRATION_CONFIRM, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String printPaymentInfo(Registration registration) {
        try {
            EmailPaymentInfo context = EmailPaymentInfo.builder()
                    .build();

            return executeTemplate(Templates.Emails.PAYMENT_INFO, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String printPaymentConfirm(Registration registration) {
        try {
            EmailPaymentConfirm context = EmailPaymentConfirm.builder()
                    .build();

            return executeTemplate(Templates.Emails.PAYMENT_CONFIRM, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getPayBySquareURL(BigDecimal amount, LocalDate dueDate, String variableSymbol, String note, String iban) {
        String template = "https://api.freebysquare.sk/pay/v1/generate-png?size=150&color=3&transparent=false&amount={{amount}}&dueDate={{dueDate}}&variableSymbol={{variableSymbol}}&paymentNote={{note}}&iban={{iban}}&";

        HashMap<String, String> context = new HashMap<>();
        context.put("amount", new DecimalFormat("#0.##").format(amount.setScale(2, RoundingMode.HALF_DOWN)));
        context.put("dueDate", DateUtils.formatCompact(dueDate));
        context.put("variableSymbol", variableSymbol);
        context.put("note", StringUtils.urlEncode(note));
        context.put("iban", iban);

        return executeString(template, context);
    }

    private String executeTemplate(String templateName, Object context) throws Exception {
        var reader = templateLoader.getTemplate(templateName);
        var template = compiler.compile(reader);
        return template.execute(context);
    }

    private String executeString(String templateString, Object context) {
        Template template = compiler.compile(templateString);
        return template.execute(context);
    }
}
