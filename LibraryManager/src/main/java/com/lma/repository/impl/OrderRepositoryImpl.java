package com.lma.repository.impl;

import com.lma.accessor.OrderFileAccessor;
import com.lma.mapper.OrderMapper;
import com.lma.model.Order;
import com.lma.repository.OrderRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {
    private static final OrderFileAccessor orderFileAccessor = new OrderFileAccessor();

    private static final Set<Order> ordersList = new HashSet<>();


    @Override
    public void loadOrdersData() {
        orderFileAccessor.readAllLines().forEach(line -> {
            Order order = OrderMapper.mapOrderFromString(line);
            if (order != null) ordersList.add(order);
            else throw new NullPointerException();
        });
    }

    @Override
    public boolean addOrder(Order order) {
        if (ordersList.add(order)) {
            try {
                orderFileAccessor.writeLine(OrderMapper.mapOrderToString(order));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

    @Override
    public Set<Order> getAllOrders() {
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

    @Override
    public Order getOrderByClientNameAndBookName(String clientName, String bookName) {
        return ordersList
                .stream()
                .filter(order ->
                        order
                                .getClient()
                                .getFullName()
                                .equals(clientName) && order
                                .getBook()
                                .getName()
                                .equals(bookName)).findFirst().orElse(null);
    }

    @Override
    public void updateOrders(Order originalOrder, Order orderToModify) {
        List<String> currentOrders = ordersList.stream().map(OrderMapper::mapOrderToString).collect(Collectors.toList());
        try {
            orderFileAccessor.replaceLine(OrderMapper.mapOrderToString(originalOrder), OrderMapper.mapOrderToString(orderToModify), currentOrders);
            ordersList.remove(originalOrder);
            ordersList.add(orderToModify);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
