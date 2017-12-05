package kmitl.tiwipabmin.eatallday.eatallday;

import org.junit.Test;

import com.tiwipabmin.eatallday.EmailValidation;
import com.tiwipabmin.eatallday.model.ValidationResult;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class EmailValidationFailTest {

    @Test
    public void emailIsEmpty() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("");

        assertFalse(result.getResult());
        assertEquals("Email is empty", result.getErrorMessage());
    }

    @Test
    public void emailIsNull() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate(null);

        assertFalse(result.getResult());
        assertEquals("Email is null", result.getErrorMessage());
    }

    @Test
    public void emailRandomString() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("asfasf.sdf@sdaf");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void emailMissingName(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("@gmail.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void emailMissingDomain(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("test@.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void emailMissingEnd(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("test@test.");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void emailNameBeginsWithASpecialCharacter(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("#test@test.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void emailHaveDoubleAmpersand(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("test@@test.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void emailHaveDoubleDot(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("test@gmail..com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void emailNameHaveSpecialCharacter(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("tes#a%bm^in@gmail.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void emailDomainHaveSpecialCharacter(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("test@te$st.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }

    @Test
    public void emailDomainHaveNumeric(){
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("test@g123test.com");

        assertFalse(result.getResult());
        assertEquals("Email is invalid", result.getErrorMessage());
    }
}
