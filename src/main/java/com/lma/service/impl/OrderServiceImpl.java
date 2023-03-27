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

public class OrderServiceImpl implements OrderService {
    private static final OrderRepository orderRepository = new OrderRepositoryImpl();
    private static final ClientService clientService = new ClientServiceImpl();
    private static final BookService bookService = new BookServiceImpl();


    public OrderServiceImpl() {
        seedOrders();
    }

    @Override
    public void seedOrders() {
        try {
            orderRepository.loadOrdersData();
        }catch (NullPointerException e ){
            System.out.println("Either book or client is missing. Please, check data!");
        }
    }

    @Override
    public Boolean createOrder(String clientName, String bookName) {
        Client client = clientService.getClientByFullName(clientName);
        Book book = bookService.getBook(bookName);
        return
               client != null && book != null ?
                       orderRepository.addOrder(new Order(client,book,LocalDate.now(),LocalDate.now().plusMonths(1))) : false;
    }

    @Override
    public String getAllOrders() {
        StringBuilder builder = new StringBuilder();
        orderRepository.getAllOrders().forEach(order ->{
            builder
                    .append(order.toString())
                    .append("\n");
        });
        return builder.toString();
    }

    @Override
    public String getAllOrdersByClient(String clientName) {
        StringBuilder builder = new StringBuilder();
        orderRepository.findOrdersByClientName(clientName).forEach(order -> {
            builder
                    .append(order.toString())
                    .append("\n");
        });
              return builder.toString();
    }

    @Override
    public String getAllOrdersIssuedOn(String date) {
        StringBuilder builder = new StringBuilder();
         orderRepository.findOrderByIssueDateOn(LocalDateFormatter.stringToLocalDate(date)).forEach(order -> {
            builder
                    .append(order.toString())
                    .append("\n");
        });
        return builder.toString();
    }

    @Override
    public String getAllOrdersIssuedAfter(String date) {
        StringBuilder builder = new StringBuilder();
        orderRepository.findOrderWithIssueDateAfter(LocalDateFormatter.stringToLocalDate(date)).forEach(order -> {
            builder
                    .append(order.toString())
                    .append("\n");
        });
        return builder.toString();
    }

    @Override
    public String getAllOrdersIssuedBefore(String date) {
        StringBuilder builder = new StringBuilder();
        orderRepository.findOrderWithIssueDateBefore(LocalDateFormatter.stringToLocalDate(date)).forEach(order -> {
            builder
                    .append(order.toString())
                    .append("\n");
        });
        return builder.toString();
    }
}
