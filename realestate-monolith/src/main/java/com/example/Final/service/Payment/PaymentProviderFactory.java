package com.example.Final.service.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PaymentProviderFactory {
    @Autowired
    private ApplicationContext applicationContext;

    public PaymentProvider createPaymentProvider(String paymentMethod) {
        switch (paymentMethod) {
            case "vnpay":
                return applicationContext.getBean(VNPayPayment.class);
            case "momo":
                return applicationContext.getBean(MomoPayment.class);
            default:
                return null;
        }
    }
}
