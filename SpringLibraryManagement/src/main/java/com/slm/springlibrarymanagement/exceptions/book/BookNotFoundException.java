package com.slm.springlibrarymanagement.exceptions.book;

public class BookNotFoundException extends RuntimeException {
    private static final String BOOK_NOT_FOUND_MESSAGE = """
            Book not found!
                
            Feel free to add it.
            """;

    @Override
    public String getMessage() {
        return BOOK_NOT_FOUND_MESSAGE;
    }
}
