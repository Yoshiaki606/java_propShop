package com.example.Final.dto;

import lombok.Data;

@Data
public class PostInformationDTO {
    private int contactId;
    private String fullName;
    private String email;
    private String phone;
    private String datePost;
    private String dateEnd;
    private String typePost;
    private int daysRemaining;
    private double payment;
    private double payPerDay;
}
