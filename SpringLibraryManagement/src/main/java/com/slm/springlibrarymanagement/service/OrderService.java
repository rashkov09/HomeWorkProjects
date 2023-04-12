package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.FileForEntityNotFound;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InsufficientBookQuantityException;

public interface OrderService {
    String findAllOrders() throws NoEntriesFoundException;

    void importOrders() throws FileForEntityNotFound;

    void backupToFile() throws BackUpFailedException;

    String insertOrder(Long clientId, Long bookId, Integer bookCount) throws InsufficientBookQuantityException;
}
