package com.scalefocus.midterm.trippyapp.exception;

public class NoDataFoundException extends RuntimeException{
    private static final String NO_DATA_FOUND_MESSAGE = "No data found in database!";
    @Override
    public String getMessage() {
        return NO_DATA_FOUND_MESSAGE;
    }
}
