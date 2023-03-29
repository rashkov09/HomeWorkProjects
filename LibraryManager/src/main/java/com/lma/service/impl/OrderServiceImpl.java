package com.lma.service.impl;

import com.lma.constants.enums.IncreasePeriod;
import com.lma.model.Book;
import com.lma.model.Client;
import com.lma.model.Order;
import com.lma.repository.OrderRepository;
import com.lma.repository.impl.OrderRepositoryImpl;
import com.lma.service.BookService;
import com.lma.service.ClientService;
import com.lma.service.OrderService;
import com.lma.util.DateInputValidator;
import com.lma.util.LocalDateFormatter;

import java.time.LocalDate;

import static com.lma.constants.CustomMessages.*;
import static com.lma.constants.Paths.ORDERS_FILE_PATH;

public class OrderServiceImpl implements OrderService {
    private static final OrderRepository orderRepository = new OrderRepositoryImpl();
    private static final ClientService clientService = new ClientServiceImpl();
    private static final BookService bookService = new BookServiceImpl();
    private static final String MISSING_DATA_EXCEPTION = "Either book or client is missing while adding orders. Please, check data!";
    private static final String ORDER_ADDED_SUCCESSFULLY = "Order added successfully!";
    private static final String ORDER_ADDITION_FAILED = "Either client or book does not exist! Please, try again!";


    public OrderServiceImpl() {
        seedOrders();
    }

    @Override
    public void seedOrders() {
        try {
            orderRepository.loadOrdersData();
            orderRepository.getAllOrders().forEach(order -> clientService.getClientByFullName(order.getClient().getFullName()).addOrder(order));
            orderRepository.getAllOrders().forEach(order -> clientService.getClientByFullName(order.getClient().getFullName()).addBook(order.getBook()));
        } catch (RuntimeException e) {
            System.out.println(MISSING_DATA_EXCEPTION);
        } catch (Exception e) {
            System.out.printf(FILE_NOT_FOUND_MESSAGE, ORDERS_FILE_PATH);
        }
    }

    @Override
    public String addOrder(String clientName, String bookName) {
        if (bookService.hasAvailableBooks()) {
            try {
                Client client = clientService.getClientByFullName(clientName);
                if (client == null) {
                    throw new RuntimeException();
                }
                Book book = bookService.getBook(bookName);
                if (book == null) {
                    throw new RuntimeException();
                }
                client.addBook(book);
                Order order = new Order(client, book, LocalDate.now());
                if (orderRepository.addOrder(order)) {
                    client.addOrder(order);
                }
                // TODO implement book order connection
                if (bookService.removeBook(book)) {
                    System.out.println("Book removed!");
                }
            } catch (RuntimeException e) {
                return ORDER_ADDITION_FAILED;
            }
            return ORDER_ADDED_SUCCESSFULLY;
        } else {
            return "No available book to order!";
        }
    }

    @Override
    public String getAllOrders() {
        StringBuilder builder = new StringBuilder();
        orderRepository.getAllOrders().forEach(order -> builder
                .append(order.toString())
                .append("\n"));
        return builder.toString();
    }

    @Override
    public String getAllOrdersByClient(String clientName) {
        StringBuilder builder = new StringBuilder();
        orderRepository.findOrdersByClientName(clientName).forEach(order -> builder
                .append(order.toString())
                .append("\n"));
        return builder.toString();
    }

    @Override
    public String getAllOrdersIssuedOn(String date) {
        StringBuilder builder = new StringBuilder();

        if (DateInputValidator.validate(date)) {
            orderRepository.findOrderByIssueDateOn(LocalDateFormatter.stringToLocalDate(date)).forEach(order -> builder
                    .append(order.toString())
                    .append("\n"));
            return builder.isEmpty() ? String.format(NO_ORDERS_ON_MESSAGE, date) : builder.toString();
        }
        return INVALID_DATE_MESSAGE;
    }

    @Override
    public String getAllOrdersIssuedAfter(String date) {
        StringBuilder builder = new StringBuilder();
        if (DateInputValidator.validate(date)) {
            orderRepository.findOrderWithIssueDateAfter(LocalDateFormatter.stringToLocalDate(date)).forEach(order -> builder
                    .append(order.toString())
                    .append("\n"));
            return builder.isEmpty() ? String.format(NO_ORDERS_AFTER_MESSAGE, date) : builder.toString();
        }
        return INVALID_DATE_MESSAGE;
    }

    @Override
    public String getAllOrdersIssuedBefore(String date) {
        StringBuilder builder = new StringBuilder();
        if (DateInputValidator.validate(date)) {
            orderRepository.findOrderWithIssueDateBefore(LocalDateFormatter.stringToLocalDate(date)).forEach(order -> builder
                    .append(order.toString())
                    .append("\n"));
            return builder.isEmpty() ? String.format(NO_ORDERS_BEFORE_MESSAGE, date) : builder.toString();
        }
        return INVALID_DATE_MESSAGE;
    }

    @Override
    public String extendOrderDueDate(String clientName, String bookName, int count, IncreasePeriod period) {
        Order originalOrder = orderRepository.getOrderByClientNameAndBookName(clientName, bookName);
        Order modifiedOrder = new Order(originalOrder.getClient(), originalOrder.getBook(), originalOrder.getIssueDate());
        LocalDate oldDate = originalOrder.getDueDate();
        switch (period) {
            case DAY -> modifiedOrder.setDueDate(oldDate.plusDays(count));
            case WEEK -> modifiedOrder.setDueDate(oldDate.plusWeeks(count));
            case MONTH -> modifiedOrder.setDueDate(oldDate.plusMonths(count));
        }

        try {
            orderRepository.updateOrders(originalOrder, modifiedOrder);
            seedOrders();

        } catch (RuntimeException e) {
            return "Order modification failed!";
        }
        return "Order modified successfully";
    }

}
