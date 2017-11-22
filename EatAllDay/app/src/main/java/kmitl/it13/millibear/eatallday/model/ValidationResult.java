package kmitl.it13.millibear.eatallday.model;

/**
 * Created by tiwip on 11/23/2017.
 */

public class ValidationResult {

    private boolean result;
    private String errorMessage;

    public ValidationResult(boolean result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public boolean getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
