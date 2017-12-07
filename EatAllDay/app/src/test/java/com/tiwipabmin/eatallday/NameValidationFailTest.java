package com.tiwipabmin.eatallday;

import org.junit.Test;

import com.tiwipabmin.eatallday.model.ValidationResult;
import com.tiwipabmin.eatallday.validation.NameValidation;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class NameValidationFailTest {

    @Test
    public void nameIsNull() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate(null);

        assertFalse(result.getResult());
        assertEquals("Name is null", result.getErrorMessage());
    }

    @Test
    public void nameIsEmptyString() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("");

        assertFalse(result.getResult());
        assertEquals("Name is empty string", result.getErrorMessage());
    }

    @Test
    public void nameContainNumber() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("1Som2chai3");

        assertFalse(result.getResult());
        assertEquals("Name contain non alphabet characters", result.getErrorMessage());
    }

    @Test
    public void nameContainSpecialCharacter() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("SomYing###");

        assertFalse(result.getResult());
        assertEquals("Name contain non alphabet characters", result.getErrorMessage());
    }

    @Test
    public void nameIsNonEnglish() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("สมสวย รวยทรัพย์");

        assertFalse(result.getResult());
        assertEquals("Name contain non alphabet characters", result.getErrorMessage());
    }
}
