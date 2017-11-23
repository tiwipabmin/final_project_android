package kmitl.it13.millibear.eatallday;

import kmitl.it13.millibear.eatallday.model.ValidationResult;
import kmitl.it13.millibear.eatallday.myexception.NameValidationException;
import kmitl.it13.millibear.eatallday.myexception.PasswordValidationException;

public class PasswordValidation {

    public ValidationResult validate(String password){
        try {
            validatePasswordIsNull(password);
            validatePasswordIsEmptyString(password);
            validatePasswordIsPassword(password);
            validatePasswordHaveLengthEightUp(password);

        } catch (Exception exception){
            return new ValidationResult(false, exception.getMessage());
        }

        return new ValidationResult(true, null);
    }

    private void validatePasswordIsNull(String password) throws PasswordValidationException {
        if(password == null) {
            throw new PasswordValidationException("Password is null");
        }
    }

    private void validatePasswordIsEmptyString(String password) throws PasswordValidationException {
        if(password.isEmpty()) {
            throw new PasswordValidationException("Password is empty input");
        }
    }

    private void validatePasswordIsPassword(String password) throws PasswordValidationException {
        if(!password.matches("^[ A-Za-z0-9]+$")) {
            throw new PasswordValidationException("Password is invalid");
        }
    }

    private void validatePasswordHaveLengthEightUp(String password) throws PasswordValidationException {
        if(!password.matches("^[ A-Za-z0-9]{8,}$")) {
            throw new PasswordValidationException("Password have at least more than 8 character");
        }
    }
}
