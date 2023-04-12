package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.service.BookService;
import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.service.OrderService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import com.slm.springlibrarymanagement.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConsoleView implements ConsoleView {
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 7;
    private static final String ORDERS_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all orders
                    2. Print orders for client
                    3. Print orders issued on
                    4. Print orders issued before
                    5. Print orders issued after
                    6. Extend order due date
                    7. Add order
                        
                    0. Back
                    """;
    private static final String CLIENT_NAME_INPUT_MESSAGE = "Please, insert client name: ";
    private static final String BOOK_COUNT_MESSAGE = "How many books would you like to order?";
    private final ExtendDueDateConsoleView extendDueDateConsoleView;
    private final OrderService orderService;
    private final ClientService clientService;
    private final BookService bookService;

    @Autowired
    public OrderConsoleView(ExtendDueDateConsoleView extendDueDateConsoleView, OrderService orderService, ClientService clientService, BookService bookService) {
        this.extendDueDateConsoleView = extendDueDateConsoleView;
        this.orderService = orderService;
        this.clientService = clientService;
        this.bookService = bookService;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
        System.out.println(ORDERS_OPTION_MESSAGE);
        System.out.print("Please choose an option: ");
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                invoker.showItemMenu(this);
                return;
            case 1:
                printAllOrders();
                break;
            case 2:
                // printAllOrdersForClient();
                break;
            case 3:
                //  printAllOrdersOn();
                break;
            case 4:
                //  printAllOrdersBefore();
                break;
            case 5:
                // printAllOrdersAfter();
                break;
            case 6:
                //  extendOrderDueDate();
                break;
            case 7:
                addOrder();
                break;
        }
        showItemMenu(invoker);
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
        System.out.println("Please, choose client ID from list above:");
        Long clientId = ConsoleReader.readLong();
        try {
            System.out.println(bookService.findAllBooks());
        } catch (NoEntriesFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Please, choose book ID from list above:");
        Long bookId = ConsoleReader.readLong();
        System.out.println("Please, insert number of books:");
        Integer bookCount = ConsoleReader.readInt();
        try {
            System.out.println(orderService.insertOrder(clientId, bookId, bookCount));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
