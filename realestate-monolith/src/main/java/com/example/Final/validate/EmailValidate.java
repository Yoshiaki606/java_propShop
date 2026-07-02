package com.example.Final.validate;

import java.util.regex.Pattern;

public class EmailValidate implements IValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public String validate(String input) {
        if (input == null || input.isEmpty()) {
            return "Email cannot be empty";
        }
        if (!EMAIL_PATTERN.matcher(input).matches()) {
            return "Invalid email format";
        }
        return "";
    }
}