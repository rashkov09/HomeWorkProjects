package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.constants.IncreasePeriod;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidDateException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InsufficientBookQuantityException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidNumberOfCopies;
import com.slm.springlibrarymanagement.exceptions.order.OrderNotFoundException;
import com.slm.springlibrarymanagement.model.entities.Client;

import java.sql.SQLException;

public interface OrderService {
    String findAllOrders() throws NoEntriesFoundException;

    void loadBookData() throws SQLException;

    void backupToFile() throws BackUpFailedException;

    String insertOrder(Long clientId, Long bookId, Integer bookCount) throws InsufficientBookQuantityException, InvalidNumberOfCopies;

    String findAllOrdersByClient(Client clientById) throws NoEntriesFoundException;

    String findOrdersByIssueDate(String date) throws InvalidDateException, NoEntriesFoundException;

    String findOrdersWithIssueDateBefore(String date) throws NoEntriesFoundException, InvalidDateException;

    String findOrdersWithIssueDateAfter(String date) throws InvalidDateException, NoEntriesFoundException;

    String extendOrderDueDate(Long id, int count, IncreasePeriod period) throws OrderNotFoundException;
}
