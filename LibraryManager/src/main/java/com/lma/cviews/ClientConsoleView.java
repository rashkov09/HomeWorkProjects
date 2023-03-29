package com.lma.cviews;

import com.lma.service.ClientService;
import com.lma.service.impl.ClientServiceImpl;
import com.lma.util.ConsoleRangeReader;
import com.lma.util.ConsoleReader;

import static com.lma.constants.CustomMessages.BOOK_NAME_INPUT_MESSAGE;
import static com.lma.constants.CustomMessages.CHOOSE_AN_OPTION_MESSAGE;

public class ClientConsoleView implements ConsoleView {
    private  final static ConsoleView mainConsoleView = new MainMenuView();
    private static final ClientService clientService = new ClientServiceImpl();
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 5;
    private static final String CLIENT_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all clients
                    2. Search for a client by first name
                    3. Search for a client by last name
                    4. Search for a client by book
                    5. Add client
                    
                    0. Back
                    """;

    private static final String CLIENT_FIRSTNAME_INPUT_MESSAGE = "Please, insert client first name: ";
    private static final String CLIENT_LASTNAME_INPUT_MESSAGE = "Please, insert client last name: ";


    @Override
    public void showItemMenu() {
        System.out.println(CLIENT_OPTION_MESSAGE);
        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                mainConsoleView.showItemMenu();
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
                printClientByBook();
                break;
            case 5:
                addClient();
                break;
        }
        showItemMenu();
    }

    private void printClientByLastName() {
        System.out.println(CLIENT_LASTNAME_INPUT_MESSAGE);
        String lastName = ConsoleReader.readString();
        System.out.println(clientService.getClientByLastName(lastName));
    }

    private void printClientByBook() {
        System.out.println(BOOK_NAME_INPUT_MESSAGE);
        String bookName = ConsoleReader.readString();
        System.out.println(clientService.getClientByBookName(bookName));
    }

    private void printClientByFirstName() {
        System.out.println(CLIENT_FIRSTNAME_INPUT_MESSAGE);
        String firstName = ConsoleReader.readString();
        System.out.println(clientService.getClientByFirstName(firstName));
    }


    public void printAllClients() {
        System.out.println(clientService.getClients());
    }


    public void addClient() {
        System.out.println(CLIENT_FIRSTNAME_INPUT_MESSAGE);
        String firstName = ConsoleReader.readString();
        System.out.println(CLIENT_LASTNAME_INPUT_MESSAGE);
        String lastName = ConsoleReader.readString();
        System.out.println(clientService.addClient(firstName,lastName));
    }
}
