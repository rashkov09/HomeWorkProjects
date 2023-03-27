package com.lma.service;

import com.lma.model.Book;
import com.lma.model.Client;

import java.time.LocalDate;

public interface OrderService {

    void seedOrders();
    Boolean createOrder(String clientName, String bookName);
    String getAllOrders();
    String getAllOrdersByClient(String clientName);
    String getAllOrdersIssuedOn(String date);
    String getAllOrdersIssuedAfter(String date);
    String getAllOrdersIssuedBefore(String date);

}
