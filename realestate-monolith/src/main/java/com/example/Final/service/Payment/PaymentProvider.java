package com.example.Final.service.Payment;

import com.example.Final.entity.securityservice.User;

public interface PaymentProvider {
    void createUserPayment(User user, double amount);
}
