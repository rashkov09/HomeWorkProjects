package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.constants.IncreasePeriod;
import com.slm.springlibrarymanagement.controller.request.OrderRequest;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidDateException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InsufficientBookQuantityException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidNumberOfCopies;
import com.slm.springlibrarymanagement.exceptions.client.ClientNotFoundException;
import com.slm.springlibrarymanagement.exceptions.order.OrderNotFoundException;
import com.slm.springlibrarymanagement.mappers.BookMapper;
import com.slm.springlibrarymanagement.mappers.ClientMapper;
import com.slm.springlibrarymanagement.mappers.OrderMapper;
import com.slm.springlibrarymanagement.model.dto.OrderDto;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.model.entities.Order;
import com.slm.springlibrarymanagement.repository.OrderRepository;
import com.slm.springlibrarymanagement.service.BookService;
import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.service.OrderService;
import com.slm.springlibrarymanagement.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static com.slm.springlibrarymanagement.constants.messages.BookMessages.BOOK_UPDATE_FAILED_MESSAGE;
import static com.slm.springlibrarymanagement.constants.messages.OrderMessages.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final ClientMapper clientMapper;
    private final OrderMapper orderMapper;
    private final InputValidator inputValidator;
    private final StringBuilder builder;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ClientService clientService,
                            BookService bookService,
                            BookMapper bookMapper, ClientMapper clientMapper, OrderMapper orderMapper, InputValidator inputValidator,
                            StringBuilder builder) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.clientMapper = clientMapper;
        this.orderMapper = orderMapper;
        this.inputValidator = inputValidator;
        this.builder = builder;
    }



    @Override
    public List<OrderDto> findAllOrders() throws NoEntriesFoundException {
        List<OrderDto> orderDtos = orderMapper.mapToDtoList(orderRepository.findAllOrders());
        if (orderDtos.isEmpty()) {
            throw new NoEntriesFoundException();
        }
        return orderDtos;
    }


    @Override
    public void backupToFile() throws BackUpFailedException {
        orderRepository.backupToFile();
    }

    @Override
    public Order insertOrder(OrderRequest orderRequest){
        Client client;
        Book book;
        Order order = new Order();
        client = clientMapper.mapFromDto(clientService.findClientById(String.valueOf(orderRequest.getClient().getId())));
        book = bookMapper.mapFromDto(bookService.findBookById(orderRequest.getBook().getId()));

        if (book.getNumberOfCopies() < orderRequest.getBookCount()) {
            throw new InsufficientBookQuantityException();
        }
        if (orderRequest.getBookCount() <= 0) {
            throw new InvalidNumberOfCopies();
        }
        order.setClient(client);
        order.setBook(book);
        order.setIssueDate(LocalDate.now());
        order.setBookCount(orderRequest.getBookCount());
        if (orderRepository.addOrder(order)) {
            book.removeBooks(orderRequest.getBookCount());
            try {
                bookService.updateBook(book);
            } catch (SQLException e) {
                throw new RuntimeException(BOOK_UPDATE_FAILED_MESSAGE);
            }

            return order;
        }
        return null;
    }

    @Override
    public List<OrderDto> findAllOrdersByClient(Client clientById)  {
        List<OrderDto> orderDtos = orderMapper.mapToDtoList(orderRepository.findOrdersByClientId(clientById.getId()));
        if (orderDtos.isEmpty()) {
            throw new NoEntriesFoundException();
        }
        return orderDtos;
    }

    @Override
    public String findOrdersByIssueDate(String date) throws InvalidDateException, NoEntriesFoundException {
        builder.setLength(0);
        if (inputValidator.isNotValidDate(date)) {
            throw new InvalidDateException();
        }

        List<Order> ordersByIssueDate = orderRepository.findOrdersByIssueDate(date);
        if (ordersByIssueDate.isEmpty()) {
            throw new NoEntriesFoundException();
        }
        ordersByIssueDate.forEach(order -> builder.append(order.toString()).append("\n"));

        return builder.toString();
    }

    @Override
    public String findOrdersWithIssueDateBefore(String date) throws NoEntriesFoundException, InvalidDateException {
        builder.setLength(0);
        if (inputValidator.isNotValidDate(date)) {
            throw new InvalidDateException();
        }
        List<Order> ordersWithIssueDateBefore = orderRepository.findOrdersWithIssueDateBefore(date);

        if (ordersWithIssueDateBefore.isEmpty()) {
            throw new NoEntriesFoundException();
        }

        ordersWithIssueDateBefore.forEach(order -> builder.append(order.toString()).append("\n"));
        return builder.toString();
    }

    @Override
    public String findOrdersWithIssueDateAfter(String date) throws InvalidDateException, NoEntriesFoundException {
        builder.setLength(0);
        if (inputValidator.isNotValidDate(date)) {
            throw new InvalidDateException();
        }
        List<Order> ordersWithIssueDateAfter = orderRepository.findOrdersWithIssueDateAfter(date);
        if (ordersWithIssueDateAfter.isEmpty()) {
            throw new NoEntriesFoundException();
        }
        ordersWithIssueDateAfter.forEach(order -> builder.append(order.toString()).append("\n"));
        return builder.toString();
    }

    @Override
    public String extendOrderDueDate(Long id, int count, IncreasePeriod period) throws OrderNotFoundException {
        try {
            Order order = orderRepository.findOrderById(id);
            order.extendDueDate(count, period);
            return orderRepository.updateOrder(order) ? String.format(ORDER_MODIFICATION_SUCCESS, id) : String.format(ORDER_MODIFICATION_FAILED, id);
        } catch (NoSuchElementException e) {
            throw new OrderNotFoundException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
