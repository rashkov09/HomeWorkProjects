package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import com.slm.springlibrarymanagement.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.slm.springlibrarymanagement.constants.messages.ClientMessages.*;
import static com.slm.springlibrarymanagement.constants.messages.GeneralMessages.CHOOSE_AN_OPTION_MESSAGE;

@Component
public class ClientConsoleView implements ConsoleView {
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 5;
    private final ClientService clientService;
    private final ConsoleReader consoleReader;
    private final ConsoleRangeReader consoleRangeReader;

    @Autowired
    public ClientConsoleView(ClientService clientService, ConsoleReader consoleReader, ConsoleRangeReader consoleRangeReader) {
        this.clientService = clientService;
        this.consoleReader = consoleReader;
        this.consoleRangeReader = consoleRangeReader;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
        System.out.println(CLIENT_OPTION_MESSAGE);
        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
        int choice = consoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
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
        String phoneNumber = consoleReader.readString();
        try {
            System.out.println(clientService.findClientByPhoneNumber(phoneNumber).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printClientByLastName() {
        System.out.println(CLIENT_LASTNAME_INPUT_MESSAGE);
        String lastName = consoleReader.readString();
        try {
            System.out.println(clientService.findClientsByLastName(lastName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printClientByFirstName() {
        System.out.println(CLIENT_FIRSTNAME_INPUT_MESSAGE);
        String firstName = consoleReader.readString();
        try {
            System.out.println(clientService.findClientsByFirstName(firstName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addClient() {
        System.out.println(CLIENT_FIRSTNAME_INPUT_MESSAGE);
        String firstName = consoleReader.readString();
        System.out.println(CLIENT_LASTNAME_INPUT_MESSAGE);
        String lastName = consoleReader.readString();
        System.out.println(CLIENT_ADDRESS_INPUT_MESSAGE);
        String address = consoleReader.readString();
        System.out.println(CLIENT_PHONE_NUMBER_INPUT_MESSAGE);
        String phoneNumber = consoleReader.readString();

        try {
           System.out.println(clientService.insertClient(firstName, lastName, address, phoneNumber));
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
