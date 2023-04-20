package com.slm.springlibrarymanagement.exceptions.author;

public class AuthorNotFoundException extends Exception {
    private static final String AUTHOR_NOT_FOUND_MESSAGE = "Author not found!";

    @Override
    public String getMessage() {
        return AUTHOR_NOT_FOUND_MESSAGE;
    }
}
