package com.slm.springlibrarymanagement.exceptions.client;

public class ClientNotFoundException extends RuntimeException {
    private static final String CLIENT_NOT_FOUND_MESSAGE = """
            Client with selected criteria not found!
            """;

    @Override
    public String getMessage() {
        return CLIENT_NOT_FOUND_MESSAGE;
    }
}
