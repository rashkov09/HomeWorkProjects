package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.model.entities.Order;

import java.sql.SQLException;
import java.util.List;


public interface OrderRepository {

    List<Order> findAllOrders();

    boolean addOrder(Order order);

    void loadOrderData() throws SQLException;

    void backupToFile() throws BackUpFailedException;
}
