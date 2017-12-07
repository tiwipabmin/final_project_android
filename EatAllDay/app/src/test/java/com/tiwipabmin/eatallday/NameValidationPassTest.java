package com.tiwipabmin.eatallday;

import org.junit.Test;

import com.tiwipabmin.eatallday.model.ValidationResult;
import com.tiwipabmin.eatallday.validation.NameValidation;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class NameValidationPassTest {

    @Test
    public void nameIsSingleCharacter() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("S");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

    @Test
    public void nameIsSingleWord() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("Somphong");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

    @Test
    public void nameIsFullName() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("Som Za");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

    @Test
    public void nameIsFullNameWithMiddleName() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("October November December");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }
}
