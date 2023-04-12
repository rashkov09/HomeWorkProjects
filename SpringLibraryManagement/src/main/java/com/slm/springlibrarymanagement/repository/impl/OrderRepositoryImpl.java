package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.model.entities.Order;
import com.slm.springlibrarymanagement.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void saveAll(List<Order> orderList) {

    }

    @Override
    public void addOrder(Order order) {

    }
}
