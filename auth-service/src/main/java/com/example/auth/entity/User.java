package com.example.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "email", unique = true)
    private String email;

    private String phone;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "account_balance")
    private double accountBalance;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "user_role", 
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Roles> roles;
}
