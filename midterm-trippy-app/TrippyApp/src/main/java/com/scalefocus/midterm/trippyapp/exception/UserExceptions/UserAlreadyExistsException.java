package com.scalefocus.midterm.trippyapp.exception.UserExceptions;

public class UserAlreadyExistsException extends RuntimeException{
    private static final String USER_ALREADY_EXISTS_MESSAGE = "User with username %s already exists!";
    private final String username;

    public UserAlreadyExistsException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
         return String.format(USER_ALREADY_EXISTS_MESSAGE,username);
    }
}
