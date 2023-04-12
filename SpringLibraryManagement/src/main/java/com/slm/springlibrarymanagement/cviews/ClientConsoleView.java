package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import com.slm.springlibrarymanagement.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ClientConsoleView implements ConsoleView {
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 5;
    private static final String CLIENT_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all clients
                    2. Search for a client by first name
                    3. Search for a client by last name
                    4. Search for a client by phone number
                    5. Add client
                                        
                    0. Back
                    """;
    private static final String CLIENT_FIRSTNAME_INPUT_MESSAGE = "Please, insert client first name: ";
    private static final String CLIENT_LASTNAME_INPUT_MESSAGE = "Please, insert client last name: ";
    private static final String CLIENT_ADDRESS_INPUT_MESSAGE = "Please, insert client address (not required): ";
    private static final String CLIENT_PHONE_NUMBER_INPUT_MESSAGE = "Please, insert client phone number: ";
    private final ClientService clientService;

    @Autowired
    public ClientConsoleView(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
        System.out.println(CLIENT_OPTION_MESSAGE);
        System.out.print("Please, choose an option: ");
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                invoker.showItemMenu(this);
                break;
            case 1:
                printAllClients();
                break;
            case 2:
                printClientByFirstName();
                break;
            case 3:
                printClientByLastName();
                break;
            case 4:
                printClientByPhoneNumber();
                break;
            case 5:
                addClient();
                break;
        }
        showItemMenu(invoker);
    }

    private void printClientByPhoneNumber() {
        System.out.println(CLIENT_PHONE_NUMBER_INPUT_MESSAGE);
        String phoneNumber = ConsoleReader.readString();
        try {
            System.out.println(clientService.findClientByPhoneNumber(phoneNumber).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printClientByLastName() {
        System.out.println(CLIENT_LASTNAME_INPUT_MESSAGE);
        String lastName = ConsoleReader.readString();
        try {
            System.out.println(clientService.findClientsByLastName(lastName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printClientByFirstName() {
        System.out.println(CLIENT_FIRSTNAME_INPUT_MESSAGE);
        String firstName = ConsoleReader.readString();
        try {
            System.out.println(clientService.findClientsByFirstName(firstName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addClient() {
        // TODO refactor this and addBook methods
        System.out.println(CLIENT_FIRSTNAME_INPUT_MESSAGE);
        String firstName = ConsoleReader.readString();
        System.out.println(CLIENT_LASTNAME_INPUT_MESSAGE);
        String lastName = ConsoleReader.readString();
        System.out.println(CLIENT_ADDRESS_INPUT_MESSAGE);
        String address = ConsoleReader.readString();
        System.out.println(CLIENT_PHONE_NUMBER_INPUT_MESSAGE);
        String phoneNumber = ConsoleReader.readString();

        try {
            clientService.insertClient(firstName, lastName, address, phoneNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    private void printAllClients() {
        try {
            System.out.println(clientService.findAllClients());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
