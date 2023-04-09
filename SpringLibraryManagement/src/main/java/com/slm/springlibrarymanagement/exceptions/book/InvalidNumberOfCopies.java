package com.slm.springlibrarymanagement.exceptions.book;

public class InvalidNumberOfCopies extends Exception {
    private static final String INVALID_NUMBER_MESSAGE = """
            Invalid number!
                
            Please, try again!
            """;

    @Override
    public String getMessage() {
        return INVALID_NUMBER_MESSAGE;
    }
}

