package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.constants.ClassesEnum;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.exceptions.client.ClientNotFoundException;
import com.slm.springlibrarymanagement.model.entities.Order;
import com.slm.springlibrarymanagement.repository.OrderRepository;
import com.slm.springlibrarymanagement.service.BookService;
import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.service.dmo.DataLoaderService;
import com.slm.springlibrarymanagement.service.dmo.DataWriterService;
import com.slm.springlibrarymanagement.util.CustomDateFormatter;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    public final static String SELECT_ORDERS_SQL = "SELECT * FROM slm.orders";
    public final static String INERT_ORDER_SQL = "INSERT INTO slm.orders (client,book, issue_date,due_date,book_count) VALUES (?,?,?,?,?)";
    public final static String UPDATE_ORDER_SQL = "UPDATE slm.orders SET due_date =?, stamp_modified=? WHERE id=?";
    private static List<Order> orderList;
    public final DataLoaderService<Order> dataLoaderService;
    public final DataWriterService<Order> dataWriterService;
    public final BookService bookService;
    public final ClientService clientService;
    private final CustomDateFormatter formatter;

    public OrderRepositoryImpl(DataLoaderService<Order> dataLoaderService, DataWriterService<Order> dataWriterService, BookService bookService, ClientService clientService, CustomDateFormatter formatter) {
        this.dataLoaderService = dataLoaderService;
        this.dataWriterService = dataWriterService;
        this.bookService = bookService;
        this.clientService = clientService;
        this.formatter = formatter;
        orderList = new ArrayList<>();
    }

    @Override
    public List<Order> findAllOrders() {
        return orderList.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public boolean addOrder(Order order) {
        Long id = dataWriterService.save(INERT_ORDER_SQL, order);

        if (id != 0L) {
            order.setId(id);
            orderList.add(order);
            return true;
        }
        return false;
    }

    @Override
    public void loadOrderData() throws SQLException {
        orderList = dataLoaderService.loadDataFromDb(SELECT_ORDERS_SQL, ClassesEnum.Order);
        if (!orderList.isEmpty()) {
            orderList.forEach(order -> {
                try {
                    order.setBook(bookService.findBookById(order.getBook().getId()));
                    order.setClient(clientService.findClientById(order.getClient().getId()));

                } catch (ClientNotFoundException | BookNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            orderList = dataLoaderService.loadDataFromFile(ClassesEnum.Order);
            orderList.forEach(order -> {
                try {
                    order.setClient(clientService.findClientByFullName(order.getClient().fullName()));
                    order.setBook(bookService.findBookByName(order.getBook().getName()));
                } catch (ClientNotFoundException | BookNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            addAll();
        }
    }

    @Override
    public void backupToFile() throws BackUpFailedException {

        dataWriterService.writeDataToFile(orderList.stream().sorted().collect(Collectors.toList()), ClassesEnum.Order);
    }

    @Override
    public List<Order> findOrdersByClientId(Long id) {
        return orderList.stream().filter(order -> order.getClient().getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<Order> findOrdersByIssueDate(String date) {
        return orderList.stream().filter(order -> order.getIssueDate().isEqual(LocalDate.parse(date, formatter.getFormatter()))).collect(Collectors.toList());
    }

    @Override
    public List<Order> findOrdersWithIssueDateBefore(String date) {
        LocalDate fitterDate = LocalDate.parse(date, formatter.getFormatter());
        return orderList.stream().filter(order -> order.getIssueDate().isBefore(fitterDate)).collect(Collectors.toList());
    }

    @Override
    public List<Order> findOrdersWithIssueDateAfter(String date) {
        LocalDate fitterDate = LocalDate.parse(date, formatter.getFormatter());
        return orderList.stream().filter(order -> order.getIssueDate().isAfter(fitterDate)).collect(Collectors.toList());
    }

    @Override
    public Order findOrderById(Long id) throws NoSuchElementException {
        return orderList.stream().filter(order -> order.getId().equals(id)).findFirst().orElseThrow();
    }

    @Override
    public boolean updateOrder(Order order) throws SQLException {
        orderList.stream().filter(orderSearch -> orderSearch.getId().equals(order.getId())).findFirst().ifPresent(foundOrder -> foundOrder.updateDueDate(order.getDueDate()));
        return dataWriterService.update(UPDATE_ORDER_SQL, order, ClassesEnum.Order);
    }

    private void addAll() throws SQLException {
        dataWriterService.saveAll(INERT_ORDER_SQL, orderList, ClassesEnum.Order);
    }
}
