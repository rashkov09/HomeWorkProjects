package com.lma.service.impl;

import com.lma.model.Book;
import com.lma.model.Client;
import com.lma.model.Order;
import com.lma.repository.OrderRepository;
import com.lma.repository.impl.OrderRepositoryImpl;
import com.lma.service.BookService;
import com.lma.service.ClientService;
import com.lma.service.OrderService;
import com.lma.util.LocalDateFormatter;

import java.time.LocalDate;
import java.util.NoSuchElementException;

public class OrderServiceImpl implements OrderService {
    private static final OrderRepository orderRepository = new OrderRepositoryImpl();
    private static final ClientService clientService = new ClientServiceImpl();
    private static final BookService bookService = new BookServiceImpl();
    private static final String MISSING_DATA_EXCEPTION = "Either book or client is missing. Please, check data!";
    private static final String ORDER_ADDED_SUCCESSFULLY = "Order added successfully!";
    private static final String ORDER_ADDITION_FAILED = "Either client or book does not exist!";


    public OrderServiceImpl() {
        seedOrders();
    }

    @Override
    public void seedOrders() {
        try {
            orderRepository.loadOrdersData();
            orderRepository.getAllOrders().forEach(order -> clientService.getClientByFullName(order.getClient().getFullName()).addOrder(order));
            orderRepository.getAllOrders().forEach(order -> clientService.getClientByFullName(order.getClient().getFullName()).addBook(order.getBook()));
        } catch (NullPointerException e) {
            System.out.println(MISSING_DATA_EXCEPTION);
        }
    }

    @Override
    public String createOrder(String clientName, String bookName) {
        try {
            Client client = clientService.getClientByFullName(clientName);
            if (client == null){
                return "Client does not exist, please insert again!";
            }
            Book book = bookService.getBook(bookName);
            client.addBook(book);
            client.addOrder(orderRepository.addOrder(new Order(client, book, LocalDate.now(), LocalDate.now().plusMonths(1))));
        } catch (NoSuchElementException e) {
            return ORDER_ADDITION_FAILED;
        }
        return ORDER_ADDED_SUCCESSFULLY;
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
        orderRepository.findOrderByIssueDateOn(LocalDateFormatter.stringToLocalDate(date)).forEach(order -> builder
                .append(order.toString())
                .append("\n"));
        return builder.toString();
    }

    @Override
    public String getAllOrdersIssuedAfter(String date) {
        StringBuilder builder = new StringBuilder();
        orderRepository.findOrderWithIssueDateAfter(LocalDateFormatter.stringToLocalDate(date)).forEach(order -> builder
                .append(order.toString())
                .append("\n"));
        return builder.toString();
    }

    @Override
    public String getAllOrdersIssuedBefore(String date) {
        StringBuilder builder = new StringBuilder();
        orderRepository.findOrderWithIssueDateBefore(LocalDateFormatter.stringToLocalDate(date)).forEach(order -> builder
                .append(order.toString())
                .append("\n"));
        return builder.toString();
    }
}
