package com.example.Final.payback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Address {
    private String street;
    private String ward;
    private String district;
    private String province;
    private String fullAddress;
    private Properties properties;



    private Address(AddressBuilder builder) {

        this.street = builder.street;
        this.ward = builder.ward;
        this.district = builder.district;
        this.province = builder.province;
        this.fullAddress = builder.fullAddress;
        this.properties = builder.properties;
    }

    public static class AddressBuilder {
        private int addressId;
        private String street;
        private String ward;
        private String district;
        private String province;
        private String fullAddress;
        private Properties properties;

        public AddressBuilder() {}

        public AddressBuilder street(String street) {
            this.street = street;
            return this;
        }

        public AddressBuilder ward(String ward) {
            this.ward = ward;
            return this;
        }

        public AddressBuilder district(String district) {
            this.district = district;
            return this;
        }

        public AddressBuilder province(String province) {
            this.province = province;
            return this;
        }

        public AddressBuilder fullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
            return this;
        }

        public AddressBuilder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
