package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.accessor.OrderFileAccessor;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InsufficientBookQuantityException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidNumberOfCopies;
import com.slm.springlibrarymanagement.exceptions.client.ClientNotFoundException;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.model.entities.Order;
import com.slm.springlibrarymanagement.repository.OrderRepository;
import com.slm.springlibrarymanagement.service.BookService;
import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.service.OrderService;
import com.slm.springlibrarymanagement.util.CustomDateFormatter;
import com.slm.springlibrarymanagement.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String ORDER_FILE_FORMAT_TEMPLATE = "%d.%s_%s_%s_%s";
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final BookService bookService;
    private final InputValidator inputValidator;
    private final CustomDateFormatter formatter;
    private final OrderFileAccessor orderFileAccessor;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ClientService clientService,
                            BookService bookService,
                            InputValidator inputValidator,
                            CustomDateFormatter formatter,
                            OrderFileAccessor orderFileAccessor) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.bookService = bookService;

        this.inputValidator = inputValidator;
        this.formatter = formatter;
        this.orderFileAccessor = orderFileAccessor;

    }

    @Override
    public String findAllOrders() throws NoEntriesFoundException {
        StringBuilder builder = new StringBuilder();
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
        if (bookCount <= 0){
            throw new InvalidNumberOfCopies();
        }
        order.setClient(client);
        order.setBook(book);
        order.setIssueDate(LocalDate.now());
        order.setBookCount(bookCount);
       if( orderRepository.addOrder(order)){
           book.removeBooks(bookCount);
           try {
               bookService.updateBook(book);
           } catch (SQLException e) {
               throw new RuntimeException("Failed to update book!");
           }

           return String.format("Order of client %s for %d copies of book %s placed,successfully!", client.fullName(), bookCount, book.getName());
       }
       return "Order addition failed!";
    }
}
