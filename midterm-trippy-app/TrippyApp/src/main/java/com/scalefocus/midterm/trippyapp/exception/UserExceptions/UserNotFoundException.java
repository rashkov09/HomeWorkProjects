package com.scalefocus.midterm.trippyapp.exception.UserExceptions;

public class UserNotFoundException extends RuntimeException {
    private final static String USER_NOT_FOUND_MESSAGE = "User with %s not found!";
    private final String param;

    public UserNotFoundException(String param) {
        this.param = param;
    }

    @Override
    public String getMessage() {
        return String.format(USER_NOT_FOUND_MESSAGE, param);
    }
}
