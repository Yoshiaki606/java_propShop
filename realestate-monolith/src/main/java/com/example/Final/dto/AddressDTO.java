package com.example.Final.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private int addressId;
    private String street;
    private String ward;
    private String district;
    private String province;
    private String fullAddress;
}
