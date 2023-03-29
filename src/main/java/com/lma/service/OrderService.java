package com.lma.service;

import com.lma.constants.enums.IncreasePeriod;

public interface OrderService {

    void seedOrders();
    String addOrder(String clientName, String bookName);
    String getAllOrders();
    String getAllOrdersByClient(String clientName);
    String getAllOrdersIssuedOn(String date);
    String getAllOrdersIssuedAfter(String date);
    String getAllOrdersIssuedBefore(String date);

    String extendOrderDueDate(String clientName, String bookName, int count, IncreasePeriod period);
}
