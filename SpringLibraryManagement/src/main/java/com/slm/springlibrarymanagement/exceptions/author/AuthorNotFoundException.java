package com.slm.springlibrarymanagement.exceptions.author;

public class
AuthorNotFoundException extends RuntimeException {
    private static final String AUTHOR_NOT_FOUND_MESSAGE = "Author not found!";

    @Override
    public String getMessage() {
        return AUTHOR_NOT_FOUND_MESSAGE;
    }
}
