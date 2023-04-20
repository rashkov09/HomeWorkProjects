package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.constants.IncreasePeriod;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidDateException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InsufficientBookQuantityException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidNumberOfCopies;
import com.slm.springlibrarymanagement.exceptions.order.OrderNotFoundException;
import com.slm.springlibrarymanagement.model.dto.OrderDto;
import com.slm.springlibrarymanagement.model.entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    List<OrderDto> findAllOrders();

    void loadOrderData() throws SQLException;

    void backupToFile() throws BackUpFailedException;

    String insertOrder(Long clientId, Long bookId, Integer bookCount) throws InsufficientBookQuantityException, InvalidNumberOfCopies;

    List<OrderDto> findAllOrdersByClient(Client clientById);

    String findOrdersByIssueDate(String date) throws InvalidDateException, NoEntriesFoundException;

    String findOrdersWithIssueDateBefore(String date) throws NoEntriesFoundException, InvalidDateException;

    String findOrdersWithIssueDateAfter(String date) throws InvalidDateException, NoEntriesFoundException;

    String extendOrderDueDate(Long id, int count, IncreasePeriod period) throws OrderNotFoundException;
}
