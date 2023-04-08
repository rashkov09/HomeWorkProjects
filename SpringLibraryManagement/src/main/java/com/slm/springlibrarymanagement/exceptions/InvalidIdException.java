package com.slm.springlibrarymanagement.exceptions;

public class InvalidIdException extends Exception{
    private static final String INVALID_ID_MESSAGE = """
            Invalid ID!
                
            Please, try again! Make sure only digits are excepted for ID!
            """;

    @Override
    public String getMessage() {
        return INVALID_ID_MESSAGE;
    }
}
