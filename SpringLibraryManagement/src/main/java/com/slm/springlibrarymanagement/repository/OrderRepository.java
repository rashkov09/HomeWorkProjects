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

    List<Order> findOrdersByClientId(Long id);

    List<Order> findOrdersByIssueDate(String date);

    List<Order> findOrdersWithIssueDateBefore(String date);

    List<Order> findOrdersWithIssueDateAfter(String date);

    Order findOrderById(Long id);

    boolean updateOrder(Order order) throws SQLException;
}
