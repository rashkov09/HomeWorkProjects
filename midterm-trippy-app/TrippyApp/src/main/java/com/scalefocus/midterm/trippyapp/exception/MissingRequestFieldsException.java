package com.scalefocus.midterm.trippyapp.exception;

public class MissingRequestFieldsException extends RuntimeException {
    private static final String MISSING_FIELD_EXCEPTION = "Invalid number of fields!";

    @Override
    public String getMessage() {
        return MISSING_FIELD_EXCEPTION;
    }
}
