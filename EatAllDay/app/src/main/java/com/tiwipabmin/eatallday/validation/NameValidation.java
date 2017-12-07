package com.tiwipabmin.eatallday.validation;

import com.tiwipabmin.eatallday.model.ValidationResult;
import com.tiwipabmin.eatallday.myexception.NameValidationException;

public class NameValidation {

    public ValidationResult validate(String inputName) {
        try {

            validateNameIsNull(inputName);
            validateNameIsEmptyString(inputName);
            validateNameIsAlphabet(inputName);

        } catch (Exception e){
            return new ValidationResult(false, e.getMessage());
        }

        return new ValidationResult(true, null);
    }

    private void validateNameIsNull(String name) throws NameValidationException {
        if(name == null) {
            throw new NameValidationException("Name is null");
        }
    }

    private void validateNameIsEmptyString(String name) throws NameValidationException {
        if(name.isEmpty()) {
            throw new NameValidationException("Name is empty string");
        }
    }

    private void validateNameIsAlphabet(String name) throws NameValidationException {
        if(!name.matches("^[ A-Za-z]+$")) {
            throw new NameValidationException("Name contain non alphabet characters");
        }
    }
}
