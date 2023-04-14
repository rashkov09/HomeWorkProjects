package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.service.BookService;
import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.service.OrderService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import com.slm.springlibrarymanagement.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.slm.springlibrarymanagement.constants.messages.GeneralMessages.CHOOSE_AN_OPTION_MESSAGE;
import static com.slm.springlibrarymanagement.constants.messages.OrderMessages.*;

@Component
public class OrderConsoleView implements ConsoleView {
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 7;
    private static ConsoleView mainViewReference;
    private final ExtendDueDateConsoleView extendDueDateConsoleView;
    private final OrderService orderService;
    private final ClientService clientService;
    private final BookService bookService;
    private final ConsoleReader consoleReader;
    private final ConsoleRangeReader consoleRangeReader;

    @Autowired
    public OrderConsoleView(ExtendDueDateConsoleView extendDueDateConsoleView, OrderService orderService, ClientService clientService, BookService bookService, ConsoleReader consoleReader, ConsoleRangeReader consoleRangeReader) {
        this.extendDueDateConsoleView = extendDueDateConsoleView;
        this.orderService = orderService;
        this.clientService = clientService;
        this.bookService = bookService;
        this.consoleReader = consoleReader;
        this.consoleRangeReader = consoleRangeReader;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
        System.out.println(ORDERS_OPTION_MESSAGE);
        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
        int choice = consoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        if (mainViewReference == null) {
            mainViewReference = invoker;
        }
        switch (choice) {
            case 0:
                mainViewReference.showItemMenu(this);
                return;
            case 1:
                printAllOrders();
                break;
            case 2:
                printAllOrdersForClient();
                break;
            case 3:
                printAllOrdersIssuedOn();
                break;
            case 4:
                printAllOrdersWithIssueDateBefore();
                break;
            case 5:
                printAllOrdersWithIssueDateAfter();
                break;
            case 6:
                extendDueDateConsoleView.showItemMenu(this);
                break;
            case 7:
                addOrder();
                break;
        }
        showItemMenu(invoker);
    }


    private void printAllOrdersWithIssueDateAfter() {
        System.out.println(INSERT_DATE_MESSAGE);
        String date = consoleReader.readString();
        try {
            System.out.println(orderService.findOrdersWithIssueDateAfter(date));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printAllOrdersWithIssueDateBefore() {
        System.out.println(INSERT_DATE_MESSAGE);
        String date = consoleReader.readString();
        try {
            System.out.println(orderService.findOrdersWithIssueDateBefore(date));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printAllOrdersIssuedOn() {
        System.out.println(INSERT_DATE_MESSAGE);
        String date = consoleReader.readString();
        try {
            System.out.println(orderService.findOrdersByIssueDate(date));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printAllOrdersForClient() {
        try {
            System.out.println(clientService.findAllClients());
            System.out.println(CHOOSE_CLIENT_ID_MESSAGE);
            Long id = consoleReader.readLong();
            System.out.println(orderService.findAllOrdersByClient(clientService.findClientById(id)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void printAllOrders() {
        try {
            System.out.println(orderService.findAllOrders());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addOrder() {
        try {
            System.out.println(clientService.findAllClients());
        } catch (NoEntriesFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(CHOOSE_CLIENT_ID_MESSAGE);
        Long clientId = consoleReader.readLong();
        try {
            System.out.println(bookService.findAllBooks());
        } catch (NoEntriesFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(CHOOSE_BOOK_ID_MESSAGE);
        Long bookId = consoleReader.readLong();
        System.out.println(BOOK_COUNT_MESSAGE);
        Integer bookCount = consoleReader.readInt();
        try {
            System.out.println(orderService.insertOrder(clientId, bookId, bookCount));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
