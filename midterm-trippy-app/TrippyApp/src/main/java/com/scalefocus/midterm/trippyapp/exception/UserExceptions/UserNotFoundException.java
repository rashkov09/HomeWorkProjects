package com.scalefocus.midterm.trippyapp.exception.UserExceptions;

public class UserNotFoundException extends RuntimeException{
    private final static String AUTHOR_NOT_FOUND_MESSAGE= "Author with id %d not found!";
    private final Long id;
    public UserNotFoundException(Long id) {
        this.id =id;
    }

    @Override
    public String getMessage() {
        return String.format(AUTHOR_NOT_FOUND_MESSAGE,id);
    }
}
