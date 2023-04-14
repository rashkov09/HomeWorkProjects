package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.service.BookService;
import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.service.OrderService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.System.exit;

@Component
public class MainMenuView implements ConsoleView {
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 4;
    private static final String OPTION_MESSAGE =
            """
                    Choose what to manage:
                    1. Books
                    2. Authors
                    3. Clients
                    4. Orders
                                        
                    0. Exit
                    """;
    private static boolean IS_LOADED;
    private final ConsoleView authorConsoleView;
    private final ConsoleView bookConsoleView;
    private final ConsoleView clientConsoleView;
    private final ConsoleView orderConsoleView;
    private final AuthorService authorService;
    private final BookService bookService;
    private final ClientService clientService;
    private final OrderService orderService;

    @Autowired
    public MainMenuView(ConsoleView authorConsoleView, ConsoleView bookConsoleView,
                        ConsoleView clientConsoleView, ConsoleView orderConsoleView, AuthorService authorService, BookService bookService, ClientService clientService, OrderService orderService) {
        this.authorConsoleView = authorConsoleView;
        this.bookConsoleView = bookConsoleView;
        this.clientConsoleView = clientConsoleView;
        this.orderConsoleView = orderConsoleView;
        this.authorService = authorService;
        this.bookService = bookService;
        this.clientService = clientService;
        this.orderService = orderService;
        IS_LOADED = false;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
        if (!IS_LOADED) {
            try {
                authorService.loadAuthorData();
                clientService.loadClientData();
                bookService.loadBookData();
                orderService.loadBookData();
                IS_LOADED = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(OPTION_MESSAGE);
        System.out.print("Please choose an option: ");
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                backup();
                exit(1);
            case 1:
                bookConsoleView.showItemMenu(this);
                break;
            case 2:
                authorConsoleView.showItemMenu(this);
                break;
            case 3:
                clientConsoleView.showItemMenu(this);
                break;
            case 4:
                orderConsoleView.showItemMenu(this);
                break;
        }
    }

    private void backup() {
        try {
            authorService.backupToFile();
        } catch (BackUpFailedException e) {
            System.out.println(e.getMessage());
        }
        try {
            bookService.backupToFile();
        } catch (BackUpFailedException e) {
            System.out.println(e.getMessage());
        }
        try {
            clientService.backupToFile();
        } catch (BackUpFailedException e) {
            System.out.println(e.getMessage());
        }
        try {
            orderService.backupToFile();
        } catch (BackUpFailedException e) {
            System.out.println(e.getMessage());
        }

    }
}
