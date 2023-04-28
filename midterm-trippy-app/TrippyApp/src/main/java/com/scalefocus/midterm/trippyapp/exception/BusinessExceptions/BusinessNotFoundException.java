package com.scalefocus.midterm.trippyapp.exception.BusinessExceptions;

public class BusinessNotFoundException extends RuntimeException {
    private static final String BUSINESS_NOT_FOUND_MESSAGE = "Business with %s not found!";
    private final String param;

    public BusinessNotFoundException(String message) {
        this.param = message;
    }

    @Override
    public String getMessage() {
        return String.format(BUSINESS_NOT_FOUND_MESSAGE, param);
    }
}
