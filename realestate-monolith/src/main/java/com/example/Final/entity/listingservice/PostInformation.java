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
@Table(name = "post_information")
@EqualsAndHashCode(exclude = "properties")
public class PostInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
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

    @OneToOne
    @JoinColumn(name = "post_property_id")
    private Properties properties;

    private PostInformation(Builder builder) {
        this.contactId = builder.contactId;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.datePost = builder.datePost;
        this.dateEnd = builder.dateEnd;
        this.typePost = builder.typePost;
        this.daysRemaining = builder.daysRemaining;
        this.payment = builder.payment;
        this.payPerDay = builder.payPerDay;
        this.properties = builder.properties;
    }

    public static class Builder {
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
        private Properties properties;

        public Builder contactId(int contactId) {
            this.contactId = contactId;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder datePost(String datePost) {
            this.datePost = datePost;
            return this;
        }

        public Builder dateEnd(String dateEnd) {
            this.dateEnd = dateEnd;
            return this;
        }

        public Builder typePost(String typePost) {
            this.typePost = typePost;
            return this;
        }

        public Builder daysRemaining(int daysRemaining) {
            this.daysRemaining = daysRemaining;
            return this;
        }

        public Builder payment(double payment) {
            this.payment = payment;
            return this;
        }

        public Builder payPerDay(double payPerDay) {
            this.payPerDay = payPerDay;
            return this;
        }

        public Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public PostInformation build() {
            return new PostInformation(this);
        }
    }
}