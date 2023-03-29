package com.lma.repository;

import com.lma.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface OrderRepository {
    void loadOrdersData();

    boolean addOrder(Order order);

    Set<Order> getAllOrders();

    Set<Order> findOrdersByClientName(String clientName);

    Set<Order> findOrderByIssueDateOn(LocalDate date);

    Set<Order> findOrderWithIssueDateAfter(LocalDate date);

    Set<Order> findOrderWithIssueDateBefore(LocalDate date);

    Order getOrderByClientNameAndBookName(String clientName, String bookName);

    void updateOrders(Order originalOrder,Order orderToModify);
}
