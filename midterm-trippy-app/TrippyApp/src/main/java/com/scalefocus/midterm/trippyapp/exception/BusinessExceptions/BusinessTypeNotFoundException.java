package com.scalefocus.midterm.trippyapp.exception.BusinessExceptions;

public class BusinessTypeNotFoundException extends RuntimeException {
    private final static String NO_SUCH_TYPE_MESSAGE = "Business with type %s not found!";
    private final String param;

    public BusinessTypeNotFoundException(String param) {
        this.param = param;
    }

    @Override
    public String getMessage() {
        return String.format(NO_SUCH_TYPE_MESSAGE, param);
    }
}
