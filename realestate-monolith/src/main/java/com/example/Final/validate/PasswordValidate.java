package com.example.Final.validate;

public class PasswordValidate implements IValidator {
    @Override
    public String validate(String input) {
        if(input.isEmpty()){
            return "Please fill in this field";
        }
        if (input.length() <6) {
            return "Password must be more than 6 digit";
        }
        return "";
    }
}
