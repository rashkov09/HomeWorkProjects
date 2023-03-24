package com.lma.repository;

import com.lma.model.Order;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public interface OrderRepository {
    void loadOrdersData();

    Boolean addOrder(Order order);

    HashSet<Order> getAllOrders();

    Set<Order> findOrdersByClientName(String clientName);

    Set<Order> findOrderByIssueDateOn(LocalDate date);

    Set<Order> findOrderWithIssueDateAfter(LocalDate date);

    Set<Order> findOrderWithIssueDateBefore(LocalDate date);
}
