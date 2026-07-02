    package com.example.Final.service.Payment;

    import com.example.Final.entity.paymentservice.UserPayment;
    import com.example.Final.entity.paymentservice.UserPaymentDetails;
    import com.example.Final.entity.securityservice.User;
    import com.example.Final.repository.UserPaymentDetailsRepo;
    import com.example.Final.repository.UserPaymentRepo;
    import com.example.Final.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.util.List;

    @Service
    public class UserPaymentService {

        @Autowired
        private UserPaymentRepo userPaymentRepo;

        public List<UserPayment> getAll() {
            return userPaymentRepo.findAll();
        }

        public void deleteUserPayment(UserPayment userPayment) {
            userPaymentRepo.delete(userPayment);
        }
    }
