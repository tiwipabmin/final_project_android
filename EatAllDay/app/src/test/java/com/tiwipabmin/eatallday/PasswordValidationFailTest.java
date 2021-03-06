package com.tiwipabmin.eatallday;

import org.junit.Test;

import com.tiwipabmin.eatallday.model.ValidationResult;
import com.tiwipabmin.eatallday.validation.PasswordValidation;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class PasswordValidationFailTest {

    @Test
    public void passwordIsNull() {
        PasswordValidation passwordValidation = new PasswordValidation();
        ValidationResult result = passwordValidation.validate(null);

        assertFalse(result.getResult());
        assertEquals("Password is null", result.getErrorMessage());
    }

    @Test
    public void passwordIsEmptyInput() {
        PasswordValidation passwordValidation = new PasswordValidation();
        ValidationResult result = passwordValidation.validate("");

        assertFalse(result.getResult());
        assertEquals("Password is empty input", result.getErrorMessage());
    }

    @Test
    public void passwordContainSpecialCharacter() {
        PasswordValidation passwordValidation = new PasswordValidation();
        ValidationResult result = passwordValidation.validate("Secret123!@#$");

        assertFalse(result.getResult());
        assertEquals("Password is invalid", result.getErrorMessage());
    }

    @Test
    public void passwordIsNonEnglish() {
        PasswordValidation passwordValidation = new PasswordValidation();
        ValidationResult result = passwordValidation.validate("ความลับนะจ๊ะ");

        assertFalse(result.getResult());
        assertEquals("Password is invalid", result.getErrorMessage());
    }

    @Test
    public void isPasswordHaveLengthSevenUp(){
        PasswordValidation passwordValidation = new PasswordValidation();
        ValidationResult result = passwordValidation.validate("secret");

        assertFalse(result.getResult());
        assertEquals("Password have at least more than 8 character", result.getErrorMessage());
    }
}
