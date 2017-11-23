package kmitl.it13.millibear.eatallday;

import org.junit.Test;

import kmitl.it13.millibear.eatallday.model.ValidationResult;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class EmailValidationPassTest {

    @Test
    public void emailStandart() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("test@test.com");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

    @Test
    public void emailNameWithDot() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("t.est@test.com");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

    @Test
    public void emailEndWithDot() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("test@test.ac.th");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

    @Test
    public void emailEndStrange() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("test@test.xyz");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }
}
