package com.lma.service.impl;

import com.lma.model.Book;
import com.lma.model.Client;
import com.lma.model.Order;
import com.lma.repository.OrderRepository;
import com.lma.repository.impl.OrderRepositoryImpl;
import com.lma.service.BookService;
import com.lma.service.ClientService;
import com.lma.service.OrderService;

import java.time.LocalDate;

public class OrderServiceImpl implements OrderService {
    private static final OrderRepository orderRepository = new OrderRepositoryImpl();
    private static final ClientService clientService = new ClientServiceImpl();
    private static final BookService bookService = new BookServiceImpl();




    @Override
    public void seedOrders() {
        orderRepository.loadOrdersData();
    }

    @Override
    public Boolean createOrder(String clientName, String bookName) {
        // TODO to validate user input
        Client client = clientService.getClientByFullName(clientName);
        Book book = bookService.getBookByName(bookName);
        return orderRepository.addOrder(new Order(client,book,LocalDate.now(),LocalDate.now().plusMonths(1)));
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
    public String getAllOrdersIssuedOn(LocalDate date) {
        StringBuilder builder = new StringBuilder();
         orderRepository.findOrderByIssueDateOn(date).forEach(order -> {
            builder
                    .append(order.toString())
                    .append("\n");
        });
        return builder.toString();
    }

    @Override
    public String getAllOrdersIssuedAfter(LocalDate date) {
        StringBuilder builder = new StringBuilder();
        orderRepository.findOrderWithIssueDateAfter(date).forEach(order -> {
            builder
                    .append(order.toString())
                    .append("\n");
        });
        return builder.toString();
    }

    @Override
    public String getAllOrdersIssuedBefore(LocalDate date) {
        StringBuilder builder = new StringBuilder();
        orderRepository.findOrderWithIssueDateBefore(date).forEach(order -> {
            builder
                    .append(order.toString())
                    .append("\n");
        });
        return builder.toString();
    }
}
