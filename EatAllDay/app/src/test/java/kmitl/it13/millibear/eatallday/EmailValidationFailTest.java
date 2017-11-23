package kmitl.it13.millibear.eatallday;

import org.junit.Test;

import kmitl.it13.millibear.eatallday.model.ValidationResult;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class EmailValidationFailTest {

    @Test
    public void EmailIsEmpty() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("");

        assertFalse(result.getResult());
        assertEquals("Email is empty", result.getErrorMessage());
    }

    @Test
    public void EmailIsNull() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate(null);

        assertFalse(result.getResult());
        assertEquals("Email is null", result.getErrorMessage());
    }

    @Test
    public void EmailRandomString() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("asfasf.sdf@sdaf");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void EmailMissingName(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("@gmail.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void EmailMissingDomain(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("tiwipabmin@.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void EmailMissingEnd(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("tiwipabmin@gmail.");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void EmailNameBeginsWithASpecialAlphabet(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("#tiwipabmin@gmail.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void EmailHaveDoubleAmpersand(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("tiwipabmin@@gmail.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void EmailHaveDoubleDot(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("tiwipabmin@gmail..com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void EmailNameHaveSpecialAlphabet(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("tiwip#a%bm^in@gmail.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void EmailDomainHaveSpecialAlphabet(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("tiwipabmin@gma$il.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void EmailDomainHaveNumeric(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("tiwipabmin@g123mail.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }
}
