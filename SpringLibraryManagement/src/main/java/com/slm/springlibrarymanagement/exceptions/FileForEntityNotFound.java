package com.slm.springlibrarymanagement.exceptions;

public class FileForEntityNotFound  extends Exception{
    private static final String FILE_NOT_FOUND_MESSAGE = """
            Cannot import from file! File for %s not found!
            
            """;

    @Override
    public String getMessage() {
        return FILE_NOT_FOUND_MESSAGE;
    }
}
