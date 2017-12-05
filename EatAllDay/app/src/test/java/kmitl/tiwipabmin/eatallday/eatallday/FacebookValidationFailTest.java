package kmitl.tiwipabmin.eatallday.eatallday;

import org.junit.Test;

import com.tiwipabmin.eatallday.FacebookValidation;
import com.tiwipabmin.eatallday.model.ValidationResult;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by tiwip on 11/24/2017.
 */

public class FacebookValidationFailTest {

    @Test
    public void FacebookIsEmpty() {
        FacebookValidation facebookValidation = new FacebookValidation();
        ValidationResult result = facebookValidation.validate("");

        assertFalse(result.getResult());
        assertEquals("Facebook is empty", result.getErrorMessage());
    }

    @Test
    public void FacebookIsNull() {
        FacebookValidation facebookValidation = new FacebookValidation();
        ValidationResult result = facebookValidation.validate(null);

        assertFalse(result.getResult());
        assertEquals("Facebook is null", result.getErrorMessage());
    }
}
