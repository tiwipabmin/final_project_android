package kmitl.it13.millibear.eatallday;

import org.junit.Test;

import kmitl.it13.millibear.eatallday.model.ValidationResult;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class NameValidationFailTest {

    @Test
    public void NameIsNull() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate(null);

        assertFalse(result.getResult());
        assertEquals("Name is Null", result.getErrorMessage());
    }

    @Test
    public void NameIsEmptyString() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("");

        assertFalse(result.getResult());
        assertEquals("Name is Empty String", result.getErrorMessage());
    }

    @Test
    public void NameContainNumber() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("Somchai123");

        assertFalse(result.getResult());
        assertEquals("Name contain non Alphabet Characters", result.getErrorMessage());
    }

    @Test
    public void NameContainSpecialAlphabet() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("SomYing###");

        assertFalse(result.getResult());
        assertEquals("Name contain non Alphabet Characters", result.getErrorMessage());
    }

    @Test
    public void NameIsNonEnglish() {
        NameValidation nameValidation = new NameValidation();
        ValidationResult result = nameValidation.validate("สมสวย รวยทรัพย์");

        assertFalse(result.getResult());
        assertEquals("Name contain non Alphabet Characters", result.getErrorMessage());
    }
}
