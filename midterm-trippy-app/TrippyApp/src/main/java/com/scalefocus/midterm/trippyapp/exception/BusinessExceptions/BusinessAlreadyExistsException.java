package com.scalefocus.midterm.trippyapp.exception.BusinessExceptions;

public class BusinessAlreadyExistsException extends RuntimeException {
    private final static String BUSINESS_ALREADY_EXISTS_MESSAGE = "Business with %s already exists.";
    private final String param;

    public BusinessAlreadyExistsException(String param) {
        this.param = param;
    }

    @Override
    public String getMessage() {
        return String.format(BUSINESS_ALREADY_EXISTS_MESSAGE, param);
    }
}
