package com.slm.springlibrarymanagement.exceptions.author;

public class InvalidAuthorNameException extends Exception {
    private static final String INVALID_AUTHOR_NAME_MESSAGE = """
            Invalid author name!
                        
            Author name cannot be empty!
                        
            Author name must consist of first name and last name seperated by space.
                        
            Author name can only contain  alphabetic characters.
                
            Please try again!
            """;

    @Override
    public String getMessage() {
        return INVALID_AUTHOR_NAME_MESSAGE;
    }
}
