package com.slm.springlibrarymanagement.constants.messages;

public final class ClientMessages {
    public static final String CLIENT_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all clients
                    2. Search for a client by first name
                    3. Search for a client by last name
                    4. Search for a client by phone number
                    5. Add client
                                        
                    0. Back
                    """;
    public static final String CLIENT_FIRSTNAME_INPUT_MESSAGE = "Please, insert client first name: ";
    public static final String CLIENT_LASTNAME_INPUT_MESSAGE = "Please, insert client last name: ";
    public static final String CLIENT_ADDRESS_INPUT_MESSAGE = "Please, insert client address (not required): ";
    public static final String CLIENT_PHONE_NUMBER_INPUT_MESSAGE = "Please, insert client phone number: ";
    public static final String CLIENT_ADDED_SUCCESSFULLY_MESSAGE = "Client %s added successfully!";
    public static final String CLIENT_ADDITION_FAILED_MESSAGE = "Client was not added successfully! Please, try again!";

    public final static String CLIENT_VIEW_TEMPLATE = "%d. %s %s";
}
