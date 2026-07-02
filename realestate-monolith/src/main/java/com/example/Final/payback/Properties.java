package com.example.Final.payback;

import com.example.Final.entity.paymentservice.Payment;
import com.example.Final.entity.rentalservice.RentalHistory;
import com.example.Final.entity.salesservice.SalesHistory;
import com.example.Final.entity.securityservice.User;
import com.example.Final.observer.PropertyObserver;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Properties {

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

    private User user;

    private List<Images> listImages;

    private Address address;

    private PostInformation postInformation;

    private List<RentalHistory> rentalHistories;

    private List<SalesHistory> salesHistories;

    private HistoryListing historyListing;

    private Payment payment;

    public static class Builder {
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
        private User user;
        private List<Images> listImages;
        private Address address;
        private PostInformation postInformation;
        private List<RentalHistory> rentalHistories;
        private List<SalesHistory> salesHistories;
        private HistoryListing historyListing;
        private Payment payment;

        public Builder propertyTitle(String propertyTitle) {
            this.propertyTitle = propertyTitle;
            return this;
        }

        public Builder propertyPrice(double propertyPrice) {
            this.propertyPrice = propertyPrice;
            return this;
        }

        public Builder propertyType(String propertyType) {
            this.propertyType = propertyType;
            return this;
        }

        public Builder propertyTypeTransaction(String propertyTypeTransaction) {
            this.propertyTypeTransaction = propertyTypeTransaction;
            return this;
        }

        public Builder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public Builder propertyInterior(String propertyInterior) {
            this.propertyInterior = propertyInterior;
            return this;
        }

        public Builder propertyLegal(String propertyLegal) {
            this.propertyLegal = propertyLegal;
            return this;
        }

        public Builder propertyDescription(String propertyDescription) {
            this.propertyDescription = propertyDescription;
            return this;
        }

        public Builder propertyFloor(int propertyFloor) {
            this.propertyFloor = propertyFloor;
            return this;
        }

        public Builder propertyStatus(String propertyStatus) {
            this.propertyStatus = propertyStatus;
            return this;
        }

        public Builder bedrooms(int bedrooms) {
            this.bedrooms = bedrooms;
            return this;
        }

        public Builder bathrooms(int bathrooms) {
            this.bathrooms = bathrooms;
            return this;
        }

        public Builder squareMeters(double squareMeters) {
            this.squareMeters = squareMeters;
            return this;
        }

        public Builder propertyPriority(int propertyPriority) {
            this.propertyPriority = propertyPriority;
            return this;
        }

        public Builder propertyLongitude(Double propertyLongitude) {
            this.propertyLongitude = propertyLongitude;
            return this;
        }

        public Builder propertyLatitude(Double propertyLatitude) {
            this.propertyLatitude = propertyLatitude;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder listImages(List<Images> listImages) {
            this.listImages = listImages;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder postInformation(PostInformation postInformation) {
            this.postInformation = postInformation;
            return this;
        }

        public Builder rentalHistories(List<RentalHistory> rentalHistories) {
            this.rentalHistories = rentalHistories;
            return this;
        }

        public Builder salesHistories(List<SalesHistory> salesHistories) {
            this.salesHistories = salesHistories;
            return this;
        }

        public Builder historyListing(HistoryListing historyListing) {
            this.historyListing = historyListing;
            return this;
        }

        public Builder payment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Properties build() {
            Properties properties = new Properties();
            properties.propertyTitle = this.propertyTitle;
            properties.propertyPrice = this.propertyPrice;
            properties.propertyType = this.propertyType;
            properties.propertyTypeTransaction = this.propertyTypeTransaction;
            properties.isAvailable = this.isAvailable;
            properties.propertyInterior = this.propertyInterior;
            properties.propertyLegal = this.propertyLegal;
            properties.propertyDescription = this.propertyDescription;
            properties.propertyFloor = this.propertyFloor;
            properties.propertyStatus = this.propertyStatus;
            properties.bedrooms = this.bedrooms;
            properties.bathrooms = this.bathrooms;
            properties.squareMeters = this.squareMeters;
            properties.propertyPriority = this.propertyPriority;
            properties.propertyLongitude = this.propertyLongitude;
            properties.propertyLatitude = this.propertyLatitude;
            properties.user = this.user;
            properties.listImages = this.listImages;
            properties.address = this.address;
            properties.postInformation = this.postInformation;
            properties.rentalHistories = this.rentalHistories;
            properties.salesHistories = this.salesHistories;
            properties.historyListing = this.historyListing;
            properties.payment = this.payment;
            return properties;
        }

    }
}

