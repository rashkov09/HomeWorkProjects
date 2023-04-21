package com.slm.springlibrarymanagement.exceptions.client;

public class InvalidClientPhoneException extends RuntimeException {
    private static final String INVALID_CLIENT_PHONE_NUMBER_MESSAGE = """
            Invalid client phone number!
               
            Phone number cannot be empty!
                
            You have to use country prefix (+888) followed by the number.
                
            Please try again!
            """;

    @Override
    public String getMessage() {
        return INVALID_CLIENT_PHONE_NUMBER_MESSAGE;
    }
}
