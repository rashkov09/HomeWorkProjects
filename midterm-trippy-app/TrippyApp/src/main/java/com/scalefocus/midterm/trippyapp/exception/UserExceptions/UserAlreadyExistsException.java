package com.scalefocus.midterm.trippyapp.exception.UserExceptions;

public class UserAlreadyExistsException extends RuntimeException {
    private static final String USER_ALREADY_EXISTS_MESSAGE = "User with username %s or email %s already exists!";
    private final String username;
    private final String email;

    public UserAlreadyExistsException(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public String getMessage() {
        return String.format(USER_ALREADY_EXISTS_MESSAGE, username, email);
    }
}
