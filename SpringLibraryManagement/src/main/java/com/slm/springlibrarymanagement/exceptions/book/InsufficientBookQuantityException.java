package com.slm.springlibrarymanagement.exceptions.book;

public class InsufficientBookQuantityException extends RuntimeException {
    private static final String NOT_ENOUGH_BOOKS_MESSAGE = """
            Not enough books!
                
            Please, try again!
            """;

    @Override
    public String getMessage() {
        return NOT_ENOUGH_BOOKS_MESSAGE;
    }

}
