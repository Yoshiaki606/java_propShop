package com.example.Final.payback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostInformation {
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
    private PostInformation(PostInformation.Builder builder) {
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


        public PostInformation.Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public PostInformation.Builder email(String email) {
            this.email = email;
            return this;
        }

        public PostInformation.Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public PostInformation.Builder datePost(String datePost) {
            this.datePost = datePost;
            return this;
        }

        public PostInformation.Builder dateEnd(String dateEnd) {
            this.dateEnd = dateEnd;
            return this;
        }

        public PostInformation.Builder typePost(String typePost) {
            this.typePost = typePost;
            return this;
        }

        public PostInformation.Builder daysRemaining(int daysRemaining) {
            this.daysRemaining = daysRemaining;
            return this;
        }

        public PostInformation.Builder payment(double payment) {
            this.payment = payment;
            return this;
        }

        public PostInformation.Builder payPerDay(double payPerDay) {
            this.payPerDay = payPerDay;
            return this;
        }

        public PostInformation.Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public PostInformation build() {
            return new PostInformation(this);
        }
    }
}

