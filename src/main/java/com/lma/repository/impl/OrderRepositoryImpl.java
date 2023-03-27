package com.lma.repository.impl;

import com.lma.model.Order;
import com.lma.repository.OrderRepository;
import com.lma.util.OrderFileAccessor;
import com.lma.util.OrderMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {
    private static final OrderFileAccessor orderFileAccessor = new OrderFileAccessor();

    private static final HashSet<Order> ordersList = new HashSet<>();


    @Override
    public void loadOrdersData() {
        orderFileAccessor.readAllLines().forEach(line ->{
            Order order = OrderMapper.mapOrderFromString(line);
            if (order != null) ordersList.add(order);
            else  throw new NullPointerException();
        } );
    }

    @Override
    public Order addOrder(Order order) {
        if (ordersList.add(order)){
            try {
                orderFileAccessor.writeLine(OrderMapper.mapOrderToString(order));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return order;
        }
        return null;
    }

    @Override
    public HashSet<Order> getAllOrders() {
        return ordersList;
    }

    @Override
    public Set<Order> findOrdersByClientName(String clientName) {
        return ordersList.stream().filter(order -> order.getClient().getFullName().equals(clientName)).collect(Collectors.toSet());
    }

    @Override
    public Set<Order> findOrderByIssueDateOn(LocalDate date) {
        return ordersList.stream().filter(order -> order.getIssueDate().equals(date)).collect(Collectors.toSet());
    }

    @Override
    public Set<Order> findOrderWithIssueDateAfter(LocalDate date) {
        return ordersList.stream().filter(order -> order.getIssueDate().isAfter(date)).collect(Collectors.toSet());
    }

    @Override
    public Set<Order> findOrderWithIssueDateBefore(LocalDate date) {
        return ordersList.stream().filter(order -> order.getIssueDate().isBefore(date)).collect(Collectors.toSet());
    }
}
