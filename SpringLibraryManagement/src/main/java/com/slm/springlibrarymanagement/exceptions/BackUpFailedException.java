package com.slm.springlibrarymanagement.exceptions;

public class BackUpFailedException extends Exception{
    private static final String BACKUP_FAILED_EXCEPTION = """
            Backup to file failed!
                
            Please, make sure that the file exist!
            """;

    @Override
    public String getMessage() {
        return BACKUP_FAILED_EXCEPTION;
    }
}
