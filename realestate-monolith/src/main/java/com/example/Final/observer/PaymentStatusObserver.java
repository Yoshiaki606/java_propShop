package com.example.Final.observer;

import com.example.Final.entity.listingservice.Properties;
import com.example.Final.service.Payment.PaymentService;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusObserver implements PropertyObserver{
    private final PaymentService paymentService;

    public PaymentStatusObserver(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @Override
    public void update(Properties property) {
        if(property.getPropertyStatus().equals("Đã thanh toán")){
            paymentService.savePaymentSuccess(property);
        } else if (property.getPropertyStatus().equals("Chưa thanh toán")) {
            paymentService.savePaymentFailure(property);
        }
    }
}
