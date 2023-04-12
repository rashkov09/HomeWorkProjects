package com.slm.springlibrarymanagement.exceptions.client;

public class InvalidClientLastNameException extends Exception {
    private static final String INVALID_CLIENT_LAST_NAME_MESSAGE = """
            Invalid client last name!
               
            Last name cannot be empty!
                
            Last name must only consist of alphabetic characters.
                
            Please try again!
            """;

    @Override
    public String getMessage() {
        return INVALID_CLIENT_LAST_NAME_MESSAGE;
    }
}
