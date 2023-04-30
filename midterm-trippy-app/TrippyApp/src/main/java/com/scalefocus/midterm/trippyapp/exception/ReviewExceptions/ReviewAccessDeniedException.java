package com.scalefocus.midterm.trippyapp.exception.ReviewExceptions;

public class ReviewAccessDeniedException extends RuntimeException {
    private final String ACCESS_DENIED_MESSAGE = "Only user that created the comment can modify or delete it!";

    @Override
    public String getMessage() {
        return ACCESS_DENIED_MESSAGE;
    }
}
