package com.slm.springlibrarymanagement.exceptions;

public class NoEntriesFoundException extends RuntimeException {
    private static final String NO_ENTRIES_FOUND_MESSAGE = """
            No entries found in database!
            """;

    @Override
    public String getMessage() {
        return NO_ENTRIES_FOUND_MESSAGE;
    }
}
