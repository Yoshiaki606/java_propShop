package com.example.Final.entity.listingservice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
@EqualsAndHashCode(exclude = "properties")
public class Address {
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    @Column(name = "street")
    private String street;
    @Column(name = "ward")
    private String ward;
    @Column(name = "district")
    private String district;
    @Column(name = "province")
    private String province;
    @Column(name = "full_address")
    private String fullAddress;
    @OneToOne
    @JoinColumn(name = "property_id")
    private Properties properties;

    private Address(AddressBuilder builder) {
        this.addressId = builder.addressId;
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

        public AddressBuilder addressId(int addressId) {
            this.addressId = addressId;
            return this;
        }

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