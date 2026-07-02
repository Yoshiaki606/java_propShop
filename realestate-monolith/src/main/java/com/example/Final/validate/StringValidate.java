package com.example.Final.validate;

public class StringValidate implements IValidator {
    @Override
    public String validate(String input) {
        if (input == null|| input.isEmpty() || input.trim().isEmpty()){
            return "This field can't be empty";
        }
        return "";
    }
}
