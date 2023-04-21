package com.slm.springlibrarymanagement.exceptions.client;

public class InvalidClientFirstNameException extends RuntimeException {
    private static final String INVALID_CLIENT_FIRST_NAME_MESSAGE = """
            Invalid client first name!
                        
            First name cannot be empty!
                        
            First name must only consist of alphabetic characters.
                
            Please try again!
            """;

    @Override
    public String getMessage() {
        return INVALID_CLIENT_FIRST_NAME_MESSAGE;
    }
}
