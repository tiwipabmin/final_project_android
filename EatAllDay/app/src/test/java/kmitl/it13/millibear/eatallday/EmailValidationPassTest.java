package kmitl.it13.millibear.eatallday;

import org.junit.Test;

import kmitl.it13.millibear.eatallday.model.ValidationResult;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by tiwip on 11/23/2017.
 */

public class EmailValidationPassTest {

    @Test
    public void EmailIsNotEmpty() {
        EmailValidation emailValidation = new EmailValidation();
        ValidationResult result = emailValidation.validate("tiwipabmin@gmail.com");

        assertTrue(result.getResult());
        assertEquals(null, result.getErrorMessage());
    }

}
