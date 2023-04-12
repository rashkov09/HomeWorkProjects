package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InsufficientBookQuantityException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidNumberOfCopies;

import java.sql.SQLException;

public interface OrderService {
    String findAllOrders() throws NoEntriesFoundException;

    void loadBookData() throws SQLException;

    void backupToFile() throws BackUpFailedException;

    String insertOrder(Long clientId, Long bookId, Integer bookCount) throws InsufficientBookQuantityException, InvalidNumberOfCopies;
}
