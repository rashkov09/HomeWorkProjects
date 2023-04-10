package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.accessor.OrderFileAccessor;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InsufficientBookQuantityException;
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
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final BookService bookService;
    private final InputValidator inputValidator;
    private final CustomDateFormatter formatter;
    private final OrderFileAccessor orderFileAccessor;


    private static final String ORDER_FILE_FORMAT_TEMPLATE = "%d.%s_%s_%s_%s";


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
        orderRepository.findAll().forEach(order -> builder.append(order.toString()).append("\n"));

        if (builder.isEmpty()){
            throw new NoEntriesFoundException();
        }
        return builder.toString();
    }

    @Override
    public void importOrders() {
        try {
            findAllOrders();
        } catch (NoEntriesFoundException e) {
            List<Order> orderList = new ArrayList<>();
            try {
                orderFileAccessor.readAllLines().forEach(line -> {
                    Order order = new Order();
                    String[] splitData = line.split("\\.\\s", 2);
                    String[] orderData = splitData[1].split("_");
                    try {
                        Client client = clientService.findClientByFullName(orderData[0]);
                        order.setClient(client);
                    } catch (Exception a) {
                        throw new RuntimeException(a.getMessage());
                    }
                    if (inputValidator.isNotValidDate(orderData[1]) || inputValidator.isNotValidDate(orderData[2])) {
                        throw new RuntimeException();
                    }
                    LocalDate issueDate = LocalDate.parse(orderData[1]);
                    order.setIssueDate(issueDate);
                    orderList.add(order);
                });

            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
            if (orderList.isEmpty()) {
                return;
            }
            orderRepository.saveAll(orderList);
        }
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        StringBuilder builder = new StringBuilder();
        orderRepository.findAll().forEach(order -> builder
                .append(String.format(ORDER_FILE_FORMAT_TEMPLATE,
                        order.getId(),
                        order.getClient().fullName(),
                        order.getBook().getName(),
                        order.getIssueDate().format(formatter.getFormatter()),
                        order.getDueDate().format(formatter.getFormatter())))
                .append("\n"));

        try {
            orderFileAccessor.writeLine(builder.toString().trim());
        } catch (IOException e) {
            throw new BackUpFailedException();
        }
    }

    @Override
    public String insertOrder(Long clientId, Long bookId, Integer bookCount) throws InsufficientBookQuantityException {
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
        order.setClient(client);
        order.setBook(book);
        order.setIssueDate(LocalDate.now());
        order.setBookCount(bookCount);
        orderRepository.save(order);
        book.setNumberOfCopies(-bookCount);
        bookService.updateBook(book);

        return String.format("Order of client %s for %d copies of book %s placed,successfully!", client.fullName(), bookCount, book.getName());
    }
}
