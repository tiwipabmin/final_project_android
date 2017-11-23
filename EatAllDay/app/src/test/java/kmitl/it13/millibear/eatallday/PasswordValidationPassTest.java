package kmitl.it13.millibear.eatallday;

import org.junit.Test;

import kmitl.it13.millibear.eatallday.model.ValidationResult;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class PasswordValidationPassTest {

    @Test
    public void passwordHaveLengthSevenUp(){
        PasswordValidation passwordValidation = new PasswordValidation();
        ValidationResult result = passwordValidation.validate("secret12");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

    @Test
    public void passwordContainAllUpperAlphabet(){
        PasswordValidation passwordValidation = new PasswordValidation();
        ValidationResult result = passwordValidation.validate("SSSSSSSS");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

    @Test
    public void passwordContainAllLowerAlphabet(){
        PasswordValidation passwordValidation = new PasswordValidation();
        ValidationResult result = passwordValidation.validate("ssssssss");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

    @Test
    public void passwordContainAllNumeric(){
        PasswordValidation passwordValidation = new PasswordValidation();
        ValidationResult result = passwordValidation.validate("0123456789");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

    @Test
    public void passwordContainAlphabetAndNumeric(){
        PasswordValidation passwordValidation = new PasswordValidation();
        ValidationResult result = passwordValidation.validate("Secret1234");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }
}
