package com.tiwipabmin.eatallday;

import com.tiwipabmin.eatallday.model.ValidationResult;
import com.tiwipabmin.eatallday.myexception.EmailValidationException;

public class EmailValidation {

    public ValidationResult validate(String email){
        try {
            validateEmailIsNull(email);
            validateEmailIsEmpty(email);
            validateEmailIsValid(email);

        } catch (Exception exception){
            return new ValidationResult(false, exception.getMessage());
        }

        return new ValidationResult(true, null);
    }

    private void validateEmailIsEmpty(String email) throws EmailValidationException {
        if(email.isEmpty()) {
            throw new EmailValidationException("Email is empty");
        }
    }

    private void validateEmailIsNull(String email) throws EmailValidationException {
        if(email == null) {
            throw new EmailValidationException("Email is null");
        }
    }

    private void validateEmailIsValid(String email) throws EmailValidationException {
        if(!email.matches("^[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*@[a-z]+(\\.[a-z]+)*(\\.[a-z]{2,})$")) {
            throw new EmailValidationException("Email is invalid");
        }
    }
}
