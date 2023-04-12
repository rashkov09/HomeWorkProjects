package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.model.entities.Order;

import java.util.List;


public interface OrderRepository {

    List<Order> findAll();

    void saveAll(List<Order> orderList);

    void addOrder(Order order);
}
