package com.slm.springlibrarymanagement.exceptions.author;

public class AuthorAlreadyExistsException extends Exception {
    private static final String DUPLICATE_AUTHOR_MESSAGE = """
            Author already exists!
                       
            Please, try again!
            """;

    @Override
    public String getMessage() {
        return DUPLICATE_AUTHOR_MESSAGE;
    }
}
