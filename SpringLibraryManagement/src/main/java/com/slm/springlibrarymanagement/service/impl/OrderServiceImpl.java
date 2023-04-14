package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.constants.IncreasePeriod;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidDateException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InsufficientBookQuantityException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidNumberOfCopies;
import com.slm.springlibrarymanagement.exceptions.client.ClientNotFoundException;
import com.slm.springlibrarymanagement.exceptions.order.OrderNotFoundException;
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
    private final InputValidator inputValidator;
    private final StringBuilder builder;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ClientService clientService,
                            BookService bookService,
                            InputValidator inputValidator,
                            StringBuilder builder) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.bookService = bookService;
        this.inputValidator = inputValidator;
        this.builder = builder;


    }

    @Override
    public String findAllOrders() throws NoEntriesFoundException {
        builder.setLength(0);
        orderRepository.findAllOrders().forEach(order -> builder.append(order.toString()).append("\n"));
        if (builder.isEmpty()) {
            throw new NoEntriesFoundException();
        }
        return builder.toString();
    }

    @Override
    public void loadBookData() throws SQLException {
        orderRepository.loadOrderData();
    }


    @Override
    public void backupToFile() throws BackUpFailedException {
        orderRepository.backupToFile();
    }

    @Override
    public String insertOrder(Long clientId, Long bookId, Integer bookCount) throws InsufficientBookQuantityException, InvalidNumberOfCopies {
        Client client;
        Book book;
        Order order = new Order();
        try {
            client = clientService.findClientById(clientId);
        } catch (ClientNotFoundException clientNotFoundException) {
            throw new RuntimeException(clientNotFoundException.getMessage());
        }
        try {
            book = bookService.findBookById(bookId);
        } catch (BookNotFoundException bookNotFoundException) {
            throw new RuntimeException(bookNotFoundException.getMessage());
        }
        if (book.getNumberOfCopies() < bookCount) {
            throw new InsufficientBookQuantityException();
        }
        if (bookCount <= 0) {
            throw new InvalidNumberOfCopies();
        }
        order.setClient(client);
        order.setBook(book);
        order.setIssueDate(LocalDate.now());
        order.setBookCount(bookCount);
        if (orderRepository.addOrder(order)) {
            book.removeBooks(bookCount);
            try {
                bookService.updateBook(book);
            } catch (SQLException e) {
                throw new RuntimeException(BOOK_UPDATE_FAILED_MESSAGE);
            }

            return String.format(ORDER_ADDED_SUCCESSFULLY_MESSAGE, client.fullName(), bookCount, book.getName());
        }
        return ORDER_ADDITION_FAILED_MESSAGE;
    }

    @Override
    public String findAllOrdersByClient(Client clientById) throws NoEntriesFoundException {
        builder.setLength(0);
        List<Order> ordersByClientId = orderRepository.findOrdersByClientId(clientById.getId());
        if (ordersByClientId.isEmpty()) {
            throw new NoEntriesFoundException();
        }
        ordersByClientId.forEach(order -> builder.append(order.toString()));
        return builder.toString();
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
            return orderRepository.updateOrder(order) ? String.format(ORDER_MODIFICATION_SUCCESS,id) : String.format(ORDER_MODIFICATION_FAILED,id);
        } catch (NoSuchElementException e) {
            throw new OrderNotFoundException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
