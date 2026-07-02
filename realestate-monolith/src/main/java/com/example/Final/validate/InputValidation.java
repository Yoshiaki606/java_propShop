package com.example.Final.validate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class InputValidation {
    private IValidator validator;

    public void setValidator(IValidator validator) {
        this.validator = validator;
    }
    public String valid(String input) {
        return validator.validate(input);
    }
}
