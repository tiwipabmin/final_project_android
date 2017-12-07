package com.tiwipabmin.eatallday.model;

/**
 * Created by tiwip on 11/23/2017.
 */

public class ValidationResult {

    private boolean mResult;
    private String mErrorMessage;

    public ValidationResult(boolean mResult, String mErrorMessage) {
        this.mResult = mResult;
        this.mErrorMessage = mErrorMessage;
    }

    public boolean getResult() {
        return mResult;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }
}
