package com.example.Final.dto;

import lombok.Data;
import java.util.List;

@Data
public class ListingDTO {
    private int propertyId;
    private String propertyTitle;
    private double propertyPrice;
    private String propertyType;
    private String propertyTypeTransaction;
    private boolean isAvailable;
    private String propertyInterior;
    private String propertyLegal;
    private String propertyDescription;
    private int propertyFloor;
    private String propertyStatus;
    private int bedrooms;
    private int bathrooms;
    private double squareMeters;
    private int propertyPriority;
    private Double propertyLongitude;
    private Double propertyLatitude;
    
    private AddressDTO address;
    private PostInformationDTO postInformation;
    private List<String> imageUrls;
}
