package com.example.Final;

import com.example.Final.entity.paymentservice.UserPayment;
import com.example.Final.entity.paymentservice.UserPaymentDetails;
import com.example.Final.entity.securityservice.User;
import com.example.Final.repository.UserPaymentDetailsRepo;
import com.example.Final.repository.UserPaymentRepo;
import com.example.Final.service.Payment.MomoPayment;
import com.example.Final.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MomoPaymentTest {

    @Mock
    private UserPaymentDetailsRepo userPaymentDetailsRepo;

    @Mock
    private UserPaymentRepo userPaymentRepo;

    @Mock
    private UserService userService;

    @InjectMocks
    private MomoPayment momoPayment;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAccountBalance(1000.0);

        UserPayment userPayment = new UserPayment();
        userPayment.setPaymentDetails(new ArrayList<>());
        user.setUserPayment(userPayment);
    }

    @Test
    void testCreateUserPayment() {
        double amount = 600.0;
        momoPayment.createUserPayment(user, amount);

        assertEquals(1540.0, user.getAccountBalance(), 0.01);
        assertEquals(1, user.getUserPayment().getPaymentDetails().size());

        UserPaymentDetails paymentDetails = user.getUserPayment().getPaymentDetails().get(0);
        assertEquals("momo", paymentDetails.getPaymentMethod());
        assertEquals(540.0, paymentDetails.getAmount(), 0.01);
        assertEquals("Đã thanh toán", paymentDetails.getStatus());

        verify(userPaymentDetailsRepo, times(1)).save(any(UserPaymentDetails.class));
        verify(userPaymentRepo, times(1)).save(any(UserPayment.class));
        verify(userService, times(1)).save(user);
    }
}