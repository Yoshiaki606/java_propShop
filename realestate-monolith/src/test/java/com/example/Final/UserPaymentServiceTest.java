package com.example.Final;

import com.example.Final.entity.paymentservice.UserPayment;
import com.example.Final.repository.UserPaymentRepo;
import com.example.Final.service.Payment.UserPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserPaymentServiceTest {

    @Mock
    private UserPaymentRepo userPaymentRepo;

    @InjectMocks
    private UserPaymentService userPaymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        UserPayment payment = new UserPayment();
        when(userPaymentRepo.findAll()).thenReturn(Collections.singletonList(payment));

        List<UserPayment> result = userPaymentService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(payment, result.get(0));
        verify(userPaymentRepo, times(1)).findAll();
    }

    @Test
    void testDeleteUserPayment() {
        UserPayment payment = new UserPayment();

        userPaymentService.deleteUserPayment(payment);

        verify(userPaymentRepo, times(1)).delete(payment);
    }
}
