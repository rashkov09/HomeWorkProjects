package com.slm.springlibrarymanagement.exceptions;

public class InvalidDateException extends RuntimeException {
    private static final String INVALID_DATE_MESSAGE = """
            Invalid date!
                
            Please, make sure date is with format dd/MM/yyyy (ex. 09/03/1932)!
            """;

    @Override
    public String getMessage() {
        return INVALID_DATE_MESSAGE;
    }
}
