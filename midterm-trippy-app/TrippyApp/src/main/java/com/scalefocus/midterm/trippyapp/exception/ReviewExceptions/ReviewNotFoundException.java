package com.scalefocus.midterm.trippyapp.exception.ReviewExceptions;

public class ReviewNotFoundException extends RuntimeException {
    private final String REVIEW_NOT_FOUND_MESSAGE = "Review not found!";

    @Override
    public String getMessage() {
        return REVIEW_NOT_FOUND_MESSAGE;
    }
}
