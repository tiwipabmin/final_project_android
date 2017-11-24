package kmitl.it13.millibear.eatallday;

import kmitl.it13.millibear.eatallday.model.ValidationResult;
import kmitl.it13.millibear.eatallday.myexception.FacebookValidationException;

public class FacebookValidation {

    public ValidationResult validate(String facebook){
        try {
            validateFacebookIsNull(facebook);
            validateFacebookIsEmpty(facebook);

        } catch (Exception exception){
            return new ValidationResult(false, exception.getMessage());
        }

        return new ValidationResult(true, null);
    }

    private void validateFacebookIsEmpty(String facebook) throws FacebookValidationException {
        if(facebook.isEmpty()) {
            throw new FacebookValidationException("Facebook is empty");
        }
    }

    private void validateFacebookIsNull(String facebook) throws FacebookValidationException {
        if(facebook == null) {
            throw new FacebookValidationException("Facebook is null");
        }
    }
}
