package com.example.Final.validate;

public class PhoneValidate implements IValidator {
    @Override
    public String validate(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Phone cannot be empty";
        }
        if (!input.matches("\\d+")|| input.length() != 10 ){
            return "Please enter a valid phone number";
        }
        return "";
    }

}