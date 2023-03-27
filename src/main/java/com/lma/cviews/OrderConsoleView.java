package com.lma.cviews;

import com.lma.service.OrderService;
import com.lma.service.impl.OrderServiceImpl;
import com.lma.util.ConsoleRangeReader;
import com.lma.util.ConsoleReader;

import static com.lma.constatns.CustomMessages.*;


public class OrderConsoleView implements ConsoleView {
    private final static ConsoleView mainConsoleView = new MainMenuView();
    private static final OrderService orderService = new OrderServiceImpl();
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 6;
    private static final String ORDERS_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all orders
                    2. Print orders for client
                    3. Print orders issued on
                    4. Print orders issued before
                    5. Print orders issued after
                    6. Add order
                        
                    0. Back
                    """;
    private static final String CLIENT_NAME_INPUT_MESSAGE = "Please, insert client name: ";
    private static final String ORDER_ADDED_SUCCESSFULLY_MESSAGE = "Order added successfully!";
    private static final String ORDER_NOT_ADDED_MESSAGE = "Something went wrong! Order not added!";
    private static final String NO_ORDERS_BEFORE_MESSAGE = "No orders before %s found!\n";
    private static final String NO_ORDERS_AFTER_MESSAGE = "No orders after %s found!\n";

    @Override
    public void showItemMenu() {
        System.out.println(ORDERS_OPTION_MESSAGE);
        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
               mainConsoleView.showItemMenu();
                return;
            case 1:
                printAllOrders();
                break;
            case 2:
                printAllOrdersForClient();
                break;
            case 3:
                printAllOrdersOn();
                break;
            case 4:
                printAllOrdersBefore();
                break;
            case 5:
                printAllOrdersAfter();
                break;
            case 6:
                addOrder();
                break;
        }
        showItemMenu();
    }


    public void printAllOrders() {
        System.out.println(orderService.getAllOrders());
    }


    public void addOrder() {
        System.out.println(CLIENT_NAME_INPUT_MESSAGE);
        String clientName = ConsoleReader.readString();
        System.out.println(BOOK_NAME_INPUT_MESSAGE);
        String bookName = ConsoleReader.readString();
        System.out.println(orderService.createOrder(clientName, bookName));
    }

    void printAllOrdersForClient() {
        System.out.println(CLIENT_NAME_INPUT_MESSAGE);
        String clientName = ConsoleReader.readString();
        System.out.println(orderService.getAllOrdersByClient(clientName));
    }

    void printAllOrdersAfter() {
        System.out.println(DATE_INPUT_MESSAGE);
        String date = ConsoleReader.readString();
       String result = orderService.getAllOrdersIssuedAfter(date);
       if (!result.isEmpty()){
           System.out.println(result);
       }else {
           System.out.printf(NO_ORDERS_AFTER_MESSAGE, date);
       }
    }

    void printAllOrdersOn() {
        System.out.println(DATE_INPUT_MESSAGE);
        String date = ConsoleReader.readString();
        System.out.println(orderService.getAllOrdersIssuedOn(date));
    }

    void printAllOrdersBefore() {
        System.out.println(DATE_INPUT_MESSAGE);
        String date = ConsoleReader.readString();
        String result = orderService.getAllOrdersIssuedBefore(date);
        if (!result.isEmpty()) {
            System.out.println(result);
        } else {
          System.out.printf(NO_ORDERS_BEFORE_MESSAGE, date);
        }
    }

}
