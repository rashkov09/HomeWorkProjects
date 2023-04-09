package com.slm.springlibrarymanagement.exceptions.client;

public class ClientAlreadyExistsException extends Exception {
    private static final String CLIENT_ALREADY_EXISTS_MESSAGE = """
            Client with this phone number already registered!
            """;

    @Override
    public String getMessage() {
        return CLIENT_ALREADY_EXISTS_MESSAGE;
    }
}
